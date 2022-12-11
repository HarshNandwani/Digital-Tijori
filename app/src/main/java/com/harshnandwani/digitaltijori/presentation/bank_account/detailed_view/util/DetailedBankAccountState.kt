package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.presentation.util.Constants.emptyBankAccount
import com.harshnandwani.digitaltijori.presentation.util.Constants.emptyCompany

data class DetailedBankAccountState(
    val bank: Company = emptyCompany,
    val account: BankAccount = emptyBankAccount,
    val linkedCards: List<Card> = emptyList(),
    val linkedCredentials: List<Credential> = emptyList()
)
