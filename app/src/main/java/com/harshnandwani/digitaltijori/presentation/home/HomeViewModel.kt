package com.harshnandwani.digitaltijori.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.GetAllAccountsWithBankDetailsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.GetBankAccountUseCase
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreenEvent
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreenState
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllAccountsWithBankDetails: GetAllAccountsWithBankDetailsUseCase,
    private val getBankAccountUseCase: GetBankAccountUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    private var getAllAccountsJob: Job? = null

    init {
        getAllBankAccounts()
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
                        _state.value = state.value.copy(
                            filteredBankAccounts = state.value.bankAccounts.filter { entry ->
                                entry.value.holderName.contains(event.searchText, ignoreCase = true)
                                        || entry.value.alias?.contains(event.searchText, ignoreCase = true)?: false
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
                            filteredBankAccounts = state.value.bankAccounts
                        )
                    }
                }
            }
        }
    }

    private fun getAllBankAccounts(){
        getAllAccountsJob?.cancel()
        getAllAccountsJob = getAllAccountsWithBankDetails()
            .onEach { accounts ->
                _state.value = state.value.copy(
                    bankAccounts = accounts,
                    filteredBankAccounts = accounts
                )
            }
            .launchIn(viewModelScope)
    }

    suspend fun getBankAccount(id: Int): BankAccount? {
        return getBankAccountUseCase(id)
    }

}
