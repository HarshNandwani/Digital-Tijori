package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company

sealed class DetailedBankAccountEvent {
    data class LoadBank(val bank: Company) : DetailedBankAccountEvent()
    data class LoadAccount(val account: BankAccount) : DetailedBankAccountEvent()
    object DeleteBankAccount : DetailedBankAccountEvent()
}
