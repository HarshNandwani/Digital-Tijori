package com.harshnandwani.digitaltijori.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.use_case.backup_restore.CreateBackupUseCase
import com.harshnandwani.digitaltijori.domain.use_case.backup_restore.DoesAnyDataExistsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.GetAllAccountsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.GetBankAccountUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.GetAllCardsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.GetCardUseCase
import com.harshnandwani.digitaltijori.domain.use_case.credential.GetAllCredentialsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.credential.GetCredentialUseCase
import com.harshnandwani.digitaltijori.domain.use_case.preference.SetDoNotShowAboutAppUseCase
import com.harshnandwani.digitaltijori.domain.use_case.preference.ShouldShowAboutAppUseCase
import com.harshnandwani.digitaltijori.presentation.home.util.BackupStatus
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreenEvent
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreenState
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getBankAccountUseCase: GetBankAccountUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val getCardUseCase: GetCardUseCase,
    private val getAllCredentialsUseCase: GetAllCredentialsUseCase,
    private val getCredentialUseCase: GetCredentialUseCase,
    private val shouldShowAboutApp: ShouldShowAboutAppUseCase,
    private val setDoNotShowAboutApp: SetDoNotShowAboutAppUseCase,
    private val doesAnyDataExist: DoesAnyDataExistsUseCase,
    private val createBackup: CreateBackupUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state

    private var getAllAccountsJob: Job? = null
    private var getAllCredentialsJob: Job? = null
    private var getAllCardsJob: Job? = null
    private var backupJob: Job? = null

    init {
        checkIfAboutAppShouldShow()
        getAllBankAccounts()
        getAllCredentials()
        getAllCards()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnPageChanged -> {
                _state.update { it.copy(currentPage = event.page) }
            }
            is HomeScreenEvent.OnSearchTextChanged -> {
                _state.update { it.copy(searchText = event.searchText) }
                when (_state.value.currentPage) {
                    HomeScreens.BankAccountsList.route -> {
                        _state.value.filteredBankAccounts.clear()
                        _state.value.filteredBankAccounts.addAll(
                            _state.value.bankAccounts.filter {
                                it.holderName.contains(event.searchText, ignoreCase = true)
                                        || it.alias?.contains(event.searchText, ignoreCase = true) ?: false
                            }
                        )
                    }
                    HomeScreens.CardsList.route -> {
                        _state.value.filteredCards.clear()
                        _state.value.filteredCards.addAll(
                            _state.value.cards.filter {
                                it.nameOnCard.contains(event.searchText, ignoreCase = true) ||
                                it.cardAlias?.contains(event.searchText, ignoreCase = true) == true ||
                                it.variant?.contains(event.searchText, ignoreCase = true) == true
                            }
                        )
                    }
                    HomeScreens.CredentialsList.route -> {
                        _state.value.filteredCredentials.clear()
                        _state.value.filteredCredentials.addAll(
                            _state.value.credentials.filter {
                                it.username.contains(event.searchText, ignoreCase = true)
                            }
                        )
                    }
                }
            }
            is HomeScreenEvent.OnSearchDone -> {
                _state.update { it.copy(searchText = "") }
                when (_state.value.currentPage) {
                    HomeScreens.BankAccountsList.route -> {
                        _state.update { it.copy(filteredBankAccounts = _state.value.bankAccounts.toMutableList()) }
                    }
                    HomeScreens.CardsList.route -> {
                        _state.update { it.copy(filteredCards = _state.value.cards.toMutableList()) }
                    }
                    HomeScreens.CredentialsList.route -> {
                        _state.update { it.copy(filteredCredentials = _state.value.credentials.toMutableList()) }
                    }
                }
            }
            is HomeScreenEvent.ShowAboutAppToggle -> {
                _state.update { it.copy(showAboutApp = event.show) }
            }
            is HomeScreenEvent.DoNotShowAboutAppAgain -> {
                viewModelScope.launch(Dispatchers.IO) {
                    setDoNotShowAboutApp()
                }
            }
            is HomeScreenEvent.ShowBackupToggle -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (doesAnyDataExist()) {
                        _state.update { it.copy(showBackup = event.show) }
                    } else {
                        _state.update { it.copy(backupStatus = BackupStatus.NO_DATA) }
                    }
                }
            }
            is HomeScreenEvent.CreateBackup -> {
                _state.update { it.copy(backupStatus = BackupStatus.STARTED) }
                backupJob = viewModelScope.launch(Dispatchers.IO) {
                    try {
                        createBackup(event.key)
                        _state.update { it.copy(backupStatus = BackupStatus.COMPLETED) }
                    } catch (e: Exception) {
                        _state.update { it.copy(backupStatus = BackupStatus.FAILED) }
                    }
                }
            }
            HomeScreenEvent.BackupCancelled -> {
                backupJob?.cancel()
                _state.update { it.copy(backupStatus = BackupStatus.NOT_STARTED, showBackup = false) }
            }
        }
    }

    private fun checkIfAboutAppShouldShow() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(showAboutApp = shouldShowAboutApp()) }
        }
    }

    private fun getAllBankAccounts() {
        getAllAccountsJob?.cancel()
        getAllAccountsJob = viewModelScope.launch(Dispatchers.IO) {
            getAllAccountsUseCase().collectLatest { accounts ->
                _state.update {
                    it.copy(
                        bankAccounts = accounts,
                        filteredBankAccounts = accounts.toMutableList()
                    )
                }
            }
        }
    }

    private fun getAllCards() {
        getAllCardsJob?.cancel()
        getAllCardsJob = viewModelScope.launch(Dispatchers.IO) {
            getAllCardsUseCase().collectLatest { cards ->
                _state.update {
                    it.copy(cards = cards, filteredCards = cards.toMutableList())
                }
            }
        }
    }

    private fun getAllCredentials() {
        getAllCredentialsJob?.cancel()
        getAllCredentialsJob = viewModelScope.launch(Dispatchers.IO) {
            getAllCredentialsUseCase().collectLatest { credentials ->
                _state.update {
                    it.copy(
                        credentials = credentials,
                        filteredCredentials = credentials.toMutableList()
                    )
                }
            }
        }
    }

    suspend fun getBankAccount(id: Int): BankAccount? {
        return getBankAccountUseCase(id)
    }

    suspend fun getCard(id: Int): Card? {
        return getCardUseCase(id)
    }

    suspend fun getCredential(id: Int): Credential? {
        return getCredentialUseCase(id)
    }

}
