package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

data class DetailedBankAccountState(
    val bank: Company = Company(-1, "", false, false, false, -1,-1),
    val account: BankAccount = BankAccount(0,-1,"","","","",""),
    val linkedCards: List<Card> = emptyList(),
    val linkedCredentials: List<Credential> = emptyList()
)
