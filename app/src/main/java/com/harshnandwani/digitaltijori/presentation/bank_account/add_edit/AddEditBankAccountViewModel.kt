package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.AddBankAccountUseCase
import com.harshnandwani.digitaltijori.domain.use_case.company.GetAllBanksUseCase
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util.BankAccountEvent
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util.BankAccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditBankAccountViewModel @Inject constructor(
    private val getAllBanksUseCase: GetAllBanksUseCase,
    private val addBankAccountUseCase: AddBankAccountUseCase
) : ViewModel() {

    private val _state = mutableStateOf(BankAccountState())
    val state: State<BankAccountState> = _state

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
            is BankAccountEvent.BankAccountSubmit -> TODO()
        }
    }

}
