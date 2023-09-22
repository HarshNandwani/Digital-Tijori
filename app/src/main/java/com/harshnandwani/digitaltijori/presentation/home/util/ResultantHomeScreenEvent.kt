package com.harshnandwani.digitaltijori.presentation.home.util

sealed class ResultantHomeScreenEvent {
    data class BackupResult(val isSuccess: Boolean, val message: String) : ResultantHomeScreenEvent()
}
