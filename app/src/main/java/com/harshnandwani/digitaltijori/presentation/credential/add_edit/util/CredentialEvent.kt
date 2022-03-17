package com.harshnandwani.digitaltijori.presentation.credential.add_edit.util

import com.harshnandwani.digitaltijori.domain.model.Company

sealed class CredentialEvent {
    data class SelectEntity(val entity: Company) : CredentialEvent()
    data class EnteredUsername(val username: String) : CredentialEvent()
    data class EnteredPassword(val password: String) : CredentialEvent()
    object CredentialSubmit : CredentialEvent()
}
