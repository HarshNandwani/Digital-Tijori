package com.harshnandwani.digitaltijori.presentation.home.util

sealed class HomeScreenEvent {
    data class OnPageChanged(val page: String): HomeScreenEvent()
    data class OnSearchTextChanged(val searchText: String): HomeScreenEvent()
    object OnSearchDone: HomeScreenEvent()
}
