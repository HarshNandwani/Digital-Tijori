package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.AddBankAccountUseCase
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.UpdateBankAccountUseCase
import com.harshnandwani.digitaltijori.domain.use_case.company.GetAllBanksUseCase
import com.harshnandwani.digitaltijori.domain.util.InvalidBankAccountException
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util.BankAccountEvent
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util.BankAccountState
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util.BankAccountSubmitResultEvent
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBankAccountViewModel @Inject constructor(
    private val getAllBanksUseCase: GetAllBanksUseCase,
    private val addBankAccountUseCase: AddBankAccountUseCase,
    private val updateBankAccountUseCase: UpdateBankAccountUseCase
) : ViewModel() {

    private var getAllBanksJob: Job? = null

    private val _state = mutableStateOf(BankAccountState())
    val state: State<BankAccountState> = _state

    private val _eventFlow = MutableSharedFlow<BankAccountSubmitResultEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAllBanks()
    }

    fun onEvent(event: BankAccountEvent) {
        when (event) {
            is BankAccountEvent.SelectBank -> {
                _state.value = state.value.copy(
                    selectedBank = event.bank,
                )
                _state.value.bankAccount.value = state.value.bankAccount.value.copy(
                    bankId = event.bank.id
                )
            }
            is BankAccountEvent.EnteredAccountNumber -> {
                _state.value.bankAccount.value = state.value.bankAccount.value.copy(
                    accountNumber = event.accountNumber
                )
            }
            is BankAccountEvent.EnteredIFSC -> {
                _state.value.bankAccount.value = state.value.bankAccount.value.copy(
                    ifsc = event.ifsc
                )
            }
            is BankAccountEvent.EnteredHolderName -> {
                _state.value.bankAccount.value = state.value.bankAccount.value.copy(
                    holderName = event.holderName
                )
            }
            is BankAccountEvent.EnteredPhoneNumber -> {
                _state.value.bankAccount.value = state.value.bankAccount.value.copy(
                    phoneNumber = event.phoneNumber
                )
            }
            is BankAccountEvent.EnteredAlias -> {
                _state.value.bankAccount.value = state.value.bankAccount.value.copy(
                    alias = event.alias
                )
            }
            is BankAccountEvent.BankAccountSubmit -> {
                viewModelScope.launch {
                    val account = _state.value.bankAccount.value
                    if (_state.value.mode == Parameters.VAL_MODE_ADD) {
                        val data = async {
                            try {
                                addBankAccountUseCase(account)
                            } catch (e: InvalidBankAccountException) {
                                _eventFlow.emit(BankAccountSubmitResultEvent.ShowError(e.message ?: "Cannot save bank account"))
                                -1L
                            }
                        }
                        val accountId = data.await()
                        if (accountId != -1L) {
                            _eventFlow.emit(BankAccountSubmitResultEvent.BankAccountSaved(_state.value.selectedBank, accountId))
                        }
                    } else {
                        if (_state.value.previouslyEnteredBankAccount == account) {
                            _eventFlow.emit(BankAccountSubmitResultEvent.ShowError(message = "No values changed"))
                            return@launch
                        }
                        try {
                            updateBankAccountUseCase(account)
                            _eventFlow.emit(BankAccountSubmitResultEvent.BankAccountSaved(_state.value.selectedBank, account.id.toLong()))
                        } catch (e: InvalidBankAccountException) {
                            _eventFlow.emit(BankAccountSubmitResultEvent.ShowError(e.message ?: "Cannot save bank account"))
                        }
                    }
                }
            }
            is BankAccountEvent.ChangeToEditMode -> {
                getAllBanksJob?.cancel()
                _state.value = state.value.copy(
                    selectedBank = event.linkedBank,
                    mode = Parameters.VAL_MODE_EDIT
                )
                _state.value.bankAccount.value = event.account
                _state.value.previouslyEnteredBankAccount = event.account
            }
        }
    }

    private fun getAllBanks(){
        getAllBanksJob?.cancel()
        getAllBanksJob = getAllBanksUseCase()
            .onEach {  banks ->
                _state.value = state.value.copy(
                    allBanks = banks
                )
            }
            .launchIn(viewModelScope)
    }

}
