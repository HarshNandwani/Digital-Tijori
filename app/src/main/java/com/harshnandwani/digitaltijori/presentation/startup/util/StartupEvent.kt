package com.harshnandwani.digitaltijori.presentation.startup.util

import android.net.Uri

sealed class StartupEvent {
    object AuthenticationUnavailable : StartupEvent()
    object AuthenticatedSuccessfully : StartupEvent()
    data class BackupFileSelected(val uri: Uri) : StartupEvent()
    data class EnteredSecretKey(val key: String) : StartupEvent()
    object StartRestore : StartupEvent()
    object RestoreDoneOrSkipped : StartupEvent()
}
