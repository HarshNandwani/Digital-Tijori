package com.harshnandwani.digitaltijori.presentation.home.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

data class HomeScreenState(
    val bankAccounts: Map<Company, BankAccount> = emptyMap(),
    val cards: List<Card> = emptyList(),
    val credentials: Map<Company, Credential> = emptyMap(),
    val currentPage: String = HomeScreens.BankAccountsList.route,
    val filteredBankAccounts: Map<Company, BankAccount> = emptyMap(),
    val searchText: String = ""
)
