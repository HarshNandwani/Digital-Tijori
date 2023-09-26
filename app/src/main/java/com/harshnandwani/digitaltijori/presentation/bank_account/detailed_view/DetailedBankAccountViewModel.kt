package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view

import android.database.sqlite.SQLiteConstraintException
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
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

    private val _state = MutableStateFlow(DetailedBankAccountState())
    val state: StateFlow<DetailedBankAccountState> = _state

    private val _eventFlow = MutableSharedFlow<DetailedBankAccountEventResult>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: DetailedBankAccountEvent) {
        when (event) {
            is DetailedBankAccountEvent.LoadBank -> {
                _state.update { it.copy(bank = event.bank) }
            }
            is DetailedBankAccountEvent.LoadAccount -> {
                _state.update { it.copy(account = event.account) }
                getAllLinkedCards()
                getAllLinkedCredentials()
            }
            is DetailedBankAccountEvent.RefreshBankAccount -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val refreshedAccount = getBankAccountUseCase(_state.value.account.bankAccountId)
                    refreshedAccount?.let { account ->
                        _state.update { it.copy(account = account) }
                    }
                }
            }
            is DetailedBankAccountEvent.DeleteBankAccount -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        deleteBankAccountUseCase(_state.value.account)
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
        if (_state.value.bank.issuesCards.not())
            return
        viewModelScope.launch(Dispatchers.IO) {
            getCardsLinkedToAccount(_state.value.account.bankAccountId).collectLatest { cards ->
                _state.update { it.copy(linkedCards = cards) }
            }
        }
    }

    private fun getAllLinkedCredentials() {
        if (_state.value.bank.hasCredentials.not())
            return
        viewModelScope.launch(Dispatchers.IO) {
            getCredentialsLinkedToAccount(_state.value.account.bankAccountId).collectLatest { credentials ->
                _state.update { it.copy(linkedCredentials = credentials) }
            }
        }
    }

}
