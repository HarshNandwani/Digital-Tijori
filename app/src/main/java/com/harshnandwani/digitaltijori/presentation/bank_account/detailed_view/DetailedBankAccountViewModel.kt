package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.DeleteBankAccountUseCase
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountEvent
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedBankAccountViewModel @Inject constructor(
    private val deleteBankAccountUseCase: DeleteBankAccountUseCase
) : ViewModel() {

    private val _state = mutableStateOf(DetailedBankAccountState())
    val state: State<DetailedBankAccountState> = _state

    fun onEvent(event: DetailedBankAccountEvent) {
        when (event) {
            is DetailedBankAccountEvent.LoadBank -> {
                _state.value = state.value.copy(
                    bank = event.bank
                )
            }
            is DetailedBankAccountEvent.LoadAccount -> {
                _state.value = state.value.copy(
                    account = event.account
                )
            }
            is DetailedBankAccountEvent.DeleteBankAccount -> {
                viewModelScope.launch {
                    deleteBankAccountUseCase(state.value.account)
                }
            }
        }
    }
}
