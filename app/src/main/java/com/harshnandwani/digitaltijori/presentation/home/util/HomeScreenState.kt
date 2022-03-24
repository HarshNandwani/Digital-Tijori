package com.harshnandwani.digitaltijori.presentation.home.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

data class HomeScreenState(
    val bankAccounts: Map<Company, BankAccount> = emptyMap(),
    val filteredBankAccounts: Map<Company, BankAccount> = emptyMap(),
    val cards: Map<Company, Card> = emptyMap(),
    val filteredCards: Map<Company, Card> = emptyMap(),
    val credentials: Map<Company, Credential> = emptyMap(),
    val filteredCredentials: Map<Company, Credential> = emptyMap(),
    val currentPage: String = HomeScreens.BankAccountsList.route,
    val searchText: String = ""
)
