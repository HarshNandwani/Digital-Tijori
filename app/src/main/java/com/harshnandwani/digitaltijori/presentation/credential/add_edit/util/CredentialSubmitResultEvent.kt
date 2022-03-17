package com.harshnandwani.digitaltijori.presentation.credential.add_edit.util

sealed class CredentialSubmitResultEvent {
    data class ShowError(val message: String): CredentialSubmitResultEvent()
    object CredentialSaved: CredentialSubmitResultEvent()
}
