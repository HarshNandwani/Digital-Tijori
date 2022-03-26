package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util

import com.harshnandwani.digitaltijori.domain.model.Company

sealed class BankAccountSubmitResultEvent {
    data class ShowError(val message: String): BankAccountSubmitResultEvent()
    data class BankAccountSaved(val linkedBank: Company?, val accountId: Int): BankAccountSubmitResultEvent()
}
