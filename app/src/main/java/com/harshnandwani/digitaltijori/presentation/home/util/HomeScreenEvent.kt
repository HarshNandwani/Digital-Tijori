package com.harshnandwani.digitaltijori.presentation.home.util

import java.io.File

sealed class HomeScreenEvent {
    data class OnPageChanged(val page: String): HomeScreenEvent()
    data class OnSearchTextChanged(val searchText: String): HomeScreenEvent()
    object OnSearchDone: HomeScreenEvent()
    data class ShowAboutAppToggle(val show: Boolean): HomeScreenEvent()
    object DoNotShowAboutAppAgain: HomeScreenEvent()
    data class ShowBackupToggle(val show: Boolean): HomeScreenEvent()
    data class CreateBackup(val key: String, val saveBackupFile: (File) -> Unit): HomeScreenEvent()
}
