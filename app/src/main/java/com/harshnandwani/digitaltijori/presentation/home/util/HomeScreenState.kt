package com.harshnandwani.digitaltijori.presentation.home.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

data class HomeScreenState(
    val bankAccounts: List<BankAccount> = emptyList(),
    val filteredBankAccounts: MutableList<BankAccount> = mutableListOf(),
    val cards: List<Card> = emptyList(),
    val filteredCards: MutableList<Card> = mutableListOf(),
    val credentials: Map<Company, List<Credential>> = emptyMap(),
    val filteredCredentials: MutableMap<Company, List<Credential>> = mutableMapOf(),
    val currentPage: String = HomeScreens.BankAccountsList.route,
    val searchText: String = "",
    val showAboutApp: Boolean = false
)
