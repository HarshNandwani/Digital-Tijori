package com.harshnandwani.digitaltijori.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.use_case.backup_restore.CreateBackupUseCase
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllAccountsWithBankDetails: GetAllAccountsUseCase,
    private val getBankAccountUseCase: GetBankAccountUseCase,
    private val getAllCardsWithIssuerDetails: GetAllCardsUseCase,
    private val getCardUseCase: GetCardUseCase,
    private val getAllCredentialsWithEntityDetails: GetAllCredentialsUseCase,
    private val getCredentialUseCase: GetCredentialUseCase,
    private val shouldShowAboutApp: ShouldShowAboutAppUseCase,
    private val setDoNotShowAboutApp: SetDoNotShowAboutAppUseCase,
    private val createBackup: CreateBackupUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

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
                _state.value = state.value.copy(
                    currentPage = event.page
                )
            }
            is HomeScreenEvent.OnSearchTextChanged -> {
                _state.value = state.value.copy(
                    searchText = event.searchText
                )
                when (_state.value.currentPage) {
                    HomeScreens.BankAccountsList.route -> {
                        _state.value.filteredBankAccounts.clear()
                        _state.value.filteredBankAccounts.addAll(
                            state.value.bankAccounts.filter {
                                it.holderName.contains(event.searchText, ignoreCase = true)
                                        || it.alias?.contains(event.searchText, ignoreCase = true) ?: false
                            }
                        )
                    }
                    HomeScreens.CardsList.route -> {
                        _state.value.filteredCards.clear()
                        _state.value.filteredCards.addAll(
                            state.value.cards.filter {
                                it.nameOnCard.contains(event.searchText, ignoreCase = true) ||
                                it.cardAlias?.contains(event.searchText, ignoreCase = true) == true ||
                                it.variant?.contains(event.searchText, ignoreCase = true) == true
                            }
                        )
                    }
                    HomeScreens.CredentialsList.route -> {
                        _state.value.filteredCredentials.clear()
                        _state.value.filteredCredentials.addAll(
                            state.value.credentials.filter {
                                it.username.contains(event.searchText, ignoreCase = true)
                            }
                        )
                    }
                }
            }
            is HomeScreenEvent.OnSearchDone -> {
                _state.value = state.value.copy(
                    searchText = ""
                )
                when (_state.value.currentPage) {
                    HomeScreens.BankAccountsList.route -> {
                        _state.value = state.value.copy(
                            filteredBankAccounts = state.value.bankAccounts.toMutableList()
                        )
                    }
                    HomeScreens.CardsList.route -> {
                        _state.value = state.value.copy(
                            filteredCards = state.value.cards.toMutableList()
                        )
                    }
                    HomeScreens.CredentialsList.route -> {
                        _state.value = state.value.copy(
                            filteredCredentials = state.value.credentials.toMutableList()
                        )
                    }
                }
            }
            is HomeScreenEvent.ShowAboutAppToggle -> {
                _state.value = state.value.copy(
                    showAboutApp = event.show
                )
            }
            is HomeScreenEvent.DoNotShowAboutAppAgain -> {
                viewModelScope.launch(Dispatchers.IO) {
                    setDoNotShowAboutApp()
                }
            }
            is HomeScreenEvent.ShowBackupToggle -> {
                _state.value = state.value.copy(
                    showBackup = event.show
                )
            }
            is HomeScreenEvent.CreateBackup -> {
                _state.value = state.value.copy(backupStatus = BackupStatus.STARTED)
                backupJob = viewModelScope.launch(Dispatchers.IO) {
                    try {
                        createBackup(event.key)
                        _state.value = state.value.copy(backupStatus = BackupStatus.COMPLETED)
                    } catch (e: Exception) {
                        _state.value = state.value.copy(backupStatus = BackupStatus.FAILED)
                    }
                }
            }
            HomeScreenEvent.BackupCancelled -> {
                backupJob?.cancel()
                _state.value = state.value.copy(
                    backupStatus = BackupStatus.NOT_STARTED,
                    showBackup = false
                )
            }
        }
    }

    private fun checkIfAboutAppShouldShow() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                showAboutApp = shouldShowAboutApp()
            )
        }
    }

    private fun getAllBankAccounts(){
        getAllAccountsJob?.cancel()
        getAllAccountsJob = getAllAccountsWithBankDetails()
            .onEach { accounts ->
                _state.value = state.value.copy(
                    bankAccounts = accounts,
                    filteredBankAccounts = accounts.toMutableList()
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getAllCards() {
        getAllCardsJob?.cancel()
        getAllCardsJob = getAllCardsWithIssuerDetails()
            .onEach { cards ->
                _state.value = state.value.copy(
                    cards = cards,
                    filteredCards = cards.toMutableList()
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getAllCredentials() {
        getAllCredentialsJob?.cancel()
        getAllCredentialsJob = getAllCredentialsWithEntityDetails()
            .onEach { credentials ->
                _state.value = state.value.copy(
                    credentials = credentials,
                    filteredCredentials = credentials.toMutableList()
                )
            }
            .launchIn(viewModelScope)
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
