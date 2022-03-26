package com.harshnandwani.digitaltijori.presentation.home.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

data class HomeScreenState(
    val bankAccounts: Map<Company, List<BankAccount>> = emptyMap(),
    val filteredBankAccounts: MutableMap<Company, List<BankAccount>> = mutableMapOf(),
    val cards: Map<Company, Card> = emptyMap(),
    val filteredCards: Map<Company, Card> = emptyMap(),
    val credentials: Map<Company, Credential> = emptyMap(),
    val filteredCredentials: Map<Company, Credential> = emptyMap(),
    val currentPage: String = HomeScreens.BankAccountsList.route,
    val searchText: String = ""
)
