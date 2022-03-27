package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util

sealed class DetailedBankAccountEventResult {
    data class ShowError(val message: String) : DetailedBankAccountEventResult()
    object BankAccountDeleted : DetailedBankAccountEventResult()
    object CardDeleted : DetailedBankAccountEventResult()
    object CredentialDeleted : DetailedBankAccountEventResult()
}
