package com.harshnandwani.digitaltijori.presentation.home.util

sealed class HomeScreenEvent {
    data class OnPageChanged(val page: String): HomeScreenEvent()
}
