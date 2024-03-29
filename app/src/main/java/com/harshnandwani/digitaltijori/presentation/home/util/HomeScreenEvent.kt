package com.harshnandwani.digitaltijori.presentation.home.util

sealed class HomeScreenEvent {
    data class OnPageChanged(val page: String): HomeScreenEvent()
    data class OnSearchTextChanged(val searchText: String): HomeScreenEvent()
    object OnSearchDone: HomeScreenEvent()
    data class ShowAboutAppToggle(val show: Boolean): HomeScreenEvent()
    object DoNotShowAboutAppAgain: HomeScreenEvent()
    data class ShowBackupToggle(val show: Boolean): HomeScreenEvent()
    data class CreateBackup(val key: String): HomeScreenEvent()
    object BackupCancelled: HomeScreenEvent()
}
