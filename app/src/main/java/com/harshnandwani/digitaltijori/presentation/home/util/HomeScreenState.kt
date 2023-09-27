package com.harshnandwani.digitaltijori.presentation.home.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Credential

data class HomeScreenState(
    val bankAccounts: List<BankAccount> = emptyList(),
    val filteredBankAccounts: MutableList<BankAccount> = mutableListOf(),
    val cards: List<Card> = emptyList(),
    val filteredCards: MutableList<Card> = mutableListOf(),
    val credentials: List<Credential> = emptyList(),
    val filteredCredentials: MutableList<Credential> = mutableListOf(),
    val currentPage: String = HomeScreens.BankAccountsList.route,
    val searchText: String = "",
    val showAboutApp: Boolean = false,
    val showBackup: Boolean = false,
    val backupStatus: BackupStatus = BackupStatus.NOT_STARTED
)

enum class BackupStatus { NO_DATA, NOT_STARTED, STARTED, FAILED, COMPLETED }
