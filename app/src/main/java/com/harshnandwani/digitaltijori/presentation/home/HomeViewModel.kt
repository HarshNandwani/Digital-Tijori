package com.harshnandwani.digitaltijori.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.GetAllBankAccountsUseCase
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreenEvent
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllBankAccountsUseCase: GetAllBankAccountsUseCase,
    ) : ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnPageChanged -> {
                _state.value = state.value.copy(
                    currentPage = event.page
                )
            }
        }
    }

}
