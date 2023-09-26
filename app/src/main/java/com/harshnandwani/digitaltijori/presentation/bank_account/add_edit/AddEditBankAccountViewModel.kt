package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBankAccountViewModel @Inject constructor(
    private val getAllBanksUseCase: GetAllBanksUseCase,
    private val addBankAccountUseCase: AddBankAccountUseCase,
    private val updateBankAccountUseCase: UpdateBankAccountUseCase
) : ViewModel() {

    private var getAllBanksJob: Job? = null

    private val _state = MutableStateFlow(BankAccountState())
    val state: StateFlow<BankAccountState> = _state

    private val _eventFlow = MutableSharedFlow<BankAccountSubmitResultEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAllBanks()
    }

    fun onEvent(event: BankAccountEvent) {
        when (event) {
            is BankAccountEvent.SelectBank -> {
                _state.update { it.copy(selectedBank = event.bank) }
                _state.value.bankAccount.value = _state.value.bankAccount.value.copy(
                    linkedCompany = event.bank
                )
            }
            is BankAccountEvent.EnteredAccountNumber -> {
                _state.value.bankAccount.value = _state.value.bankAccount.value.copy(
                    accountNumber = event.accountNumber
                )
            }
            is BankAccountEvent.EnteredIFSC -> {
                _state.value.bankAccount.value = _state.value.bankAccount.value.copy(
                    ifsc = event.ifsc
                )
            }
            is BankAccountEvent.EnteredHolderName -> {
                _state.value.bankAccount.value = _state.value.bankAccount.value.copy(
                    holderName = event.holderName
                )
            }
            is BankAccountEvent.EnteredPhoneNumber -> {
                _state.value.bankAccount.value = _state.value.bankAccount.value.copy(
                    phoneNumber = event.phoneNumber
                )
            }
            is BankAccountEvent.EnteredAlias -> {
                _state.value.bankAccount.value = _state.value.bankAccount.value.copy(
                    alias = event.alias
                )
            }
            is BankAccountEvent.BankAccountSubmit -> {
                viewModelScope.launch(Dispatchers.IO) {
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
                        val accountId = data.await().toInt()
                        if (accountId != -1) {
                            _state.value.bankAccount.value = _state.value.bankAccount.value.copy(
                                bankAccountId = accountId
                            )
                            _eventFlow.emit(BankAccountSubmitResultEvent.BankAccountSaved(_state.value.selectedBank, accountId))
                        }
                    } else {
                        if (_state.value.previouslyEnteredBankAccount == account) {
                            _eventFlow.emit(BankAccountSubmitResultEvent.ShowError(message = "No values changed"))
                            return@launch
                        }
                        try {
                            updateBankAccountUseCase(account)
                            _eventFlow.emit(BankAccountSubmitResultEvent.BankAccountSaved(_state.value.selectedBank, account.bankAccountId))
                        } catch (e: InvalidBankAccountException) {
                            _eventFlow.emit(BankAccountSubmitResultEvent.ShowError(e.message ?: "Cannot save bank account"))
                        }
                    }
                }
            }
            is BankAccountEvent.ChangeToEditMode -> {
                getAllBanksJob?.cancel()
                _state.update {
                    it.copy(
                        selectedBank = event.linkedBank,
                        mode = Parameters.VAL_MODE_EDIT
                    )
                }
                _state.value.bankAccount.value = event.account
                _state.value.previouslyEnteredBankAccount = event.account
            }
        }
    }

    private fun getAllBanks(){
        getAllBanksJob?.cancel()
        getAllBanksJob = viewModelScope.launch(Dispatchers.IO) {
            getAllBanksUseCase().collectLatest { banks ->
                _state.update { it.copy(allBanks = banks) }
            }
        }
    }

}
