package com.harshnandwani.digitaltijori.presentation.home.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Credential

data class HomeScreenState(
    val bankAccounts: List<BankAccount> = emptyList(),
    val cards: List<Card> = emptyList(),
    val credentials: List<Credential> = emptyList(),
    val currentPage: String = HomeScreens.BankAccountsList.route
)
