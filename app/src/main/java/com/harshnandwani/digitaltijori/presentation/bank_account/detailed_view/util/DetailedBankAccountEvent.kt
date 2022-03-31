package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

sealed class DetailedBankAccountEvent {
    data class LoadBank(val bank: Company) : DetailedBankAccountEvent()
    data class LoadAccount(val account: BankAccount) : DetailedBankAccountEvent()
    object RefreshBankAccount : DetailedBankAccountEvent()
    object DeleteBankAccount : DetailedBankAccountEvent()
    data class DeleteCard(val card: Card) : DetailedBankAccountEvent()
    data class DeleteCredential(val credential: Credential) : DetailedBankAccountEvent()
}
