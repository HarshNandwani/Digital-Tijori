package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util

sealed class BankAccountSubmitResultEvent {
    data class ShowError(val message: String): BankAccountSubmitResultEvent()
    object BankAccountSaved: BankAccountSubmitResultEvent()
}
