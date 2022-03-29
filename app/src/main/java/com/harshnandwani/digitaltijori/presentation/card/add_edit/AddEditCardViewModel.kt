package com.harshnandwani.digitaltijori.presentation.card.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.use_case.card.AddCardUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.GetCardNumberLengthUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.IdentifyCardNetworkUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.UpdateCardUseCase
import com.harshnandwani.digitaltijori.domain.use_case.company.GetAllCardIssuersUseCase
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.InvalidCardException
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardEvent
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardState
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardSubmitResultEvent
import com.harshnandwani.digitaltijori.presentation.util.CardHelperFunctions
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject

@HiltViewModel
class AddEditCardViewModel @Inject constructor(
    private val getAllCardIssuersUseCase: GetAllCardIssuersUseCase,
    private val addCardUseCase: AddCardUseCase,
    private val updateCardUseCase: UpdateCardUseCase
) : ViewModel() {

    private var getAllCardIssuersJob: Job? = null

    private val _state = mutableStateOf(CardState())
    val state: State<CardState> = _state

    private val _eventFlow = MutableSharedFlow<CardSubmitResultEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val identifyCardNetwork = IdentifyCardNetworkUseCase()
    private val getCardNumberLength = GetCardNumberLengthUseCase()
    private var maxCardSizeNumber = getCardNumberLength(CardNetwork.Unknown)

    init {
        getAllCardIssuers()
    }

    fun onEvent(event: CardEvent) {
        when (event) {
            is CardEvent.SelectIssuer -> {
                _state.value = state.value.copy(
                    selectedIssuer = event.issuer
                )
                _state.value.card.value = state.value.card.value.copy(
                    companyId = event.issuer.companyId
                )
            }
            is CardEvent.LinkToAccount -> {
                _state.value.card.value = state.value.card.value.copy(
                    isLinkedToBank = true,
                    bankAccountId = event.accountId
                )
            }
            is CardEvent.SelectedCardType -> {
                _state.value.backVisible.value = false
                _state.value.card.value = state.value.card.value.copy(
                    cardType = event.cardType
                )
            }
            is CardEvent.EnteredCardNumber -> {
                _state.value.backVisible.value = false
                if (event.cardNumber.length > maxCardSizeNumber) return
                val cardNetwork = identifyCardNetwork(event.cardNumber)
                _state.value.card.value = state.value.card.value.copy(
                    cardNumber = event.cardNumber.filter {
                        it != '.' && it != ',' && it != '-' && it != ' '
                    },
                    cardNetwork = cardNetwork
                )
                maxCardSizeNumber = getCardNumberLength(cardNetwork)
            }
            is CardEvent.EnteredCardExpiry -> {
                _state.value.backVisible.value = false
                if (event.expiry.length > 4) return
                _state.value = state.value.copy(
                    expiryMonth = if (event.expiry.length > 2) event.expiry.take(2) else event.expiry
                )
                _state.value = state.value.copy(
                    expiryYear = if (event.expiry.length > 2) event.expiry.substring(2) else ""
                )
            }
            is CardEvent.EnteredCvv -> {
                _state.value.backVisible.value = true
                if (event.cvv.length > 3) return
                _state.value.card.value = state.value.card.value.copy(
                    cvv = event.cvv.filter {
                        it != '.' && it != ',' && it != '-' && it != ' '
                    }
                )
            }
            is CardEvent.EnteredNameOnCard -> {
                _state.value.backVisible.value = false
                _state.value.card.value = state.value.card.value.copy(
                    nameOnCard = event.name
                )
            }
            is CardEvent.EnteredVariant -> {
                _state.value.backVisible.value = false
                _state.value.card.value = state.value.card.value.copy(
                    variant = event.variant
                )
            }
            is CardEvent.EnteredPin -> {
                _state.value.backVisible.value = true
                if (event.pin.length > 4) return
                _state.value.card.value = state.value.card.value.copy(
                    pin = event.pin.filter {
                        it != '.' && it != ',' && it != '-' && it != ' '
                    }
                )
            }
            is CardEvent.EnteredCardAlias -> {
                _state.value.backVisible.value = false
                _state.value.card.value = state.value.card.value.copy(
                    cardAlias = event.alias
                )
            }
            is CardEvent.CardSubmit -> {
                _state.value.backVisible.value = false
                viewModelScope.launch {
                    val expiryMonth: Byte
                    val expiryYear: Byte
                    try {
                        expiryMonth = _state.value.expiryMonth.toByte()
                        expiryYear = _state.value.expiryYear.toByte()
                    } catch (_: NumberFormatException) {
                        _eventFlow.emit(CardSubmitResultEvent.ShowError(
                            message = "Invalid Expiry"
                        ))
                        return@launch
                    }
                    val card = state.value.card.value.copy(
                        expiryMonth = expiryMonth,
                        expiryYear = expiryYear
                    )

                    try {
                        if (_state.value.mode == Parameters.VAL_MODE_ADD) {
                            addCardUseCase(card)
                        } else {
                            if (_state.value.previouslyEnteredCard == card) {
                                _eventFlow.emit(CardSubmitResultEvent.ShowError("No values changes"))
                                return@launch
                            }
                            updateCardUseCase(card)
                        }
                        _eventFlow.emit(CardSubmitResultEvent.CardSaved)
                    } catch (e: InvalidCardException) {
                        if (e.message?.contains("link card with issuer") == true) {
                            _eventFlow.emit(CardSubmitResultEvent.LinkCardWithIssuer)
                        } else {
                            _eventFlow.emit(
                                CardSubmitResultEvent.ShowError(e.message ?: "Cannot save card")
                            )
                        }
                    }
                }
            }
            is CardEvent.ChangeToEditMode -> {
                getAllCardIssuersJob?.cancel()
                _state.value = state.value.copy(
                    selectedIssuer = event.issuer,
                    mode = Parameters.VAL_MODE_EDIT,
                    expiryMonth = CardHelperFunctions.getMonthAsString(event.card.expiryMonth),
                    expiryYear = event.card.expiryYear.toString(),
                    previouslyEnteredCard = event.card
                )
                _state.value.card.value = event.card
            }
        }
    }

    private fun getAllCardIssuers() {
        getAllCardIssuersJob?.cancel()
        getAllCardIssuersJob = getAllCardIssuersUseCase()
            .onEach { cardIssuers ->
                _state.value = state.value.copy(
                    allCardIssuers = cardIssuers
                )
            }
            .launchIn(viewModelScope)
    }

}
