package com.harshnandwani.digitaltijori.presentation.card.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.use_case.card.AddCardUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.IdentifyCardNetworkUseCase
import com.harshnandwani.digitaltijori.domain.use_case.company.GetAllCardIssuersUseCase
import com.harshnandwani.digitaltijori.domain.util.InvalidCardException
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardEvent
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardState
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardSubmitResultEvent
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
    private val addCardUseCase: AddCardUseCase
) : ViewModel() {

    private var getAllCardIssuersJob: Job? = null

    private val _state = mutableStateOf(CardState())
    val state: State<CardState> = _state

    private val _eventFlow = MutableSharedFlow<CardSubmitResultEvent>()
    private val eventFlow = _eventFlow.asSharedFlow()

    val identifyCardNetwork = IdentifyCardNetworkUseCase()

    init {
        getAllCardIssuers()
    }

    fun onEvent(event: CardEvent) {
        when (event) {
            is CardEvent.SelectIssuer -> {
                _state.value = state.value.copy(
                    selectedIssuer = event.issuer
                )
            }
            is CardEvent.SelectedCardType -> {
                _state.value = state.value.copy(
                    cardType = event.cardType
                )
            }
            is CardEvent.EnteredCardNumber -> {
                _state.value.cardNetwork = identifyCardNetwork(event.cardNumber)
                _state.value = state.value.copy(
                    cardNumber = event.cardNumber
                )
            }
            is CardEvent.EnteredCardExpiry -> {
                if (event.expiry.length > 4) return
                _state.value = state.value.copy(
                    expiryMonth = if (event.expiry.length > 2) event.expiry.take(2) else event.expiry
                )
                _state.value = state.value.copy(
                    expiryYear = if (event.expiry.length > 2) event.expiry.substring(2) else ""
                )
            }
            is CardEvent.EnteredCvv -> {
                try {
                    _state.value = state.value.copy(
                        cvv = event.cvv
                    )
                } catch (e: NumberFormatException) {
                    viewModelScope.launch {
                        _eventFlow.emit(CardSubmitResultEvent.InvalidCvv)
                    }
                }
            }
            is CardEvent.EnteredNameOnCard -> {
                _state.value = state.value.copy(
                    nameOnCard = event.name
                )
            }
            is CardEvent.EnteredCardAlias -> {
                _state.value = state.value.copy(
                    cardAlias = event.alias
                )
            }
            is CardEvent.CardSubmit -> {
                viewModelScope.launch {
                    val card = Card(
                        -1,
                        false,
                        null,
                        _state.value.selectedIssuer?.id,
                        _state.value.cardNumber,
                        _state.value.expiryMonth.toByte(),
                        _state.value.expiryYear.toByte(),
                        _state.value.cvv.toShort(),
                        _state.value.nameOnCard,
                        _state.value.cardNetwork,
                        _state.value.cardAlias,
                        _state.value.cardType
                    )
                    try {
                        if (_state.value.mode == Parameters.VAL_MODE_ADD) {
                            addCardUseCase(card)
                        }
                        _eventFlow.emit(CardSubmitResultEvent.CardSaved)
                    } catch (e: InvalidCardException) {
                        _eventFlow.emit(
                            CardSubmitResultEvent.ShowError(e.message ?: "Cannot save card")
                        )
                    }
                }
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
