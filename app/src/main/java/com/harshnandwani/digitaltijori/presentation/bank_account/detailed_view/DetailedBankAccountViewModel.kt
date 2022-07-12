package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.DeleteBankAccountUseCase
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.GetBankAccountUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.DeleteCardUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.GetCardsLinkedToAccountUseCase
import com.harshnandwani.digitaltijori.domain.use_case.credential.DeleteCredentialUseCase
import com.harshnandwani.digitaltijori.domain.use_case.credential.GetCredentialsLinkedToAccountUseCase
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountEvent
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountEventResult
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailedBankAccountViewModel @Inject constructor(
    private val getBankAccountUseCase: GetBankAccountUseCase,
    private val deleteBankAccountUseCase: DeleteBankAccountUseCase,
    private val getCardsLinkedToAccount: GetCardsLinkedToAccountUseCase,
    private val deleteCardUseCase: DeleteCardUseCase,
    private val getCredentialsLinkedToAccount: GetCredentialsLinkedToAccountUseCase,
    private val deleteCredentialUseCase: DeleteCredentialUseCase
) : ViewModel() {

    private val _state = mutableStateOf(DetailedBankAccountState())
    val state: State<DetailedBankAccountState> = _state

    private val _eventFlow = MutableSharedFlow<DetailedBankAccountEventResult>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: DetailedBankAccountEvent) {
        when (event) {
            is DetailedBankAccountEvent.LoadBank -> {
                _state.value = state.value.copy(
                    bank = event.bank
                )
            }
            is DetailedBankAccountEvent.LoadAccount -> {
                _state.value = state.value.copy(
                    account = event.account
                )
                getAllLinkedCards()
                getAllLinkedCredentials()
            }
            is DetailedBankAccountEvent.RefreshBankAccount -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val refreshedAccount = getBankAccountUseCase(state.value.account.bankAccountId)
                    if (refreshedAccount != null) {
                        _state.value = state.value.copy(
                            account = refreshedAccount
                        )
                    }
                }
            }
            is DetailedBankAccountEvent.DeleteBankAccount -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        deleteBankAccountUseCase(state.value.account)
                        _eventFlow.emit(DetailedBankAccountEventResult.BankAccountDeleted)
                    } catch (_: SQLiteConstraintException) {
                        _eventFlow.emit(
                            DetailedBankAccountEventResult.ShowError(
                                "Account has cards or credentials linked"
                            )
                        )
                    }
                }
            }
            is DetailedBankAccountEvent.DeleteCard -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        deleteCardUseCase(event.card)
                        _eventFlow.emit(DetailedBankAccountEventResult.CardDeleted)
                    } catch (_: Exception) {
                        _eventFlow.emit(
                            DetailedBankAccountEventResult.ShowError(
                                "Cannot delete card"
                            )
                        )
                    }
                }
            }
            is DetailedBankAccountEvent.DeleteCredential -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        deleteCredentialUseCase(event.credential)
                        _eventFlow.emit(DetailedBankAccountEventResult.CredentialDeleted)
                    } catch (_: Exception) {
                        _eventFlow.emit(
                            DetailedBankAccountEventResult.ShowError(
                                "Cannot delete credential"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getAllLinkedCards() {
        if (state.value.bank.issuesCards) {
            getCardsLinkedToAccount(state.value.account.bankAccountId)
                .onEach { cards ->
                    _state.value = state.value.copy(
                        linkedCards = cards
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    private fun getAllLinkedCredentials() {
        if (state.value.bank.hasCredentials) {
            getCredentialsLinkedToAccount(state.value.account.bankAccountId)
                .onEach { credentials ->
                    _state.value = state.value.copy(
                        linkedCredentials = credentials
                    )
                }
                .launchIn(viewModelScope)
        }
    }

}
