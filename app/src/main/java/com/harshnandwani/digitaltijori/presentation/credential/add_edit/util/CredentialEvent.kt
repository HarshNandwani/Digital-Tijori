package com.harshnandwani.digitaltijori.presentation.credential.add_edit.util

import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

sealed class CredentialEvent {
    data class SelectEntity(val entity: Company) : CredentialEvent()
    data class LinkToAccount(val linkedAccountId: Int): CredentialEvent()
    data class EnteredUsername(val username: String) : CredentialEvent()
    data class EnteredPassword(val password: String) : CredentialEvent()
    data class EnteredAlias(val alias: String) : CredentialEvent()
    object CredentialSubmit : CredentialEvent()
    data class ChangeToEditMode(val linkedEntity: Company, val credential: Credential) : CredentialEvent()
}
