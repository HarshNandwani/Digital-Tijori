package com.harshnandwani.digitaltijori.presentation.home.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount

sealed class HomeScreenEvent {
    data class OnPageChanged(val page: String): HomeScreenEvent()
    data class OnSearchTextChanged(val searchText: String): HomeScreenEvent()
    object OnSearchDone: HomeScreenEvent()
    data class OnBankAccountDelete(val account: BankAccount): HomeScreenEvent()
}
