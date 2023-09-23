package com.harshnandwani.digitaltijori.presentation.startup.util

import android.net.Uri

data class StartupState(
    val restoreEligible: Boolean? = null,
    val restoreStatus: RestoreStatus = RestoreStatus.NOT_STARTED,
    val restoreErrorMessage: String = "",
    val backupFileName: String? = null,
    val backupFileUri: Uri? = null,
    val secretKey: String = "",
    val authAvailable: Boolean = true,
    val shouldAuthenticate: Boolean? = null,
    val authSuccessful: Boolean? = null
)

enum class RestoreStatus { NOT_STARTED, STARTED, SUCCESS, FAILED }
