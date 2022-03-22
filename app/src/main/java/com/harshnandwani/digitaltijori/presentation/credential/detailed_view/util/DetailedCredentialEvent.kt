package com.harshnandwani.digitaltijori.presentation.credential.detailed_view.util

import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

sealed class DetailedCredentialEvent {
    data class LoadEntity(val entity: Company) : DetailedCredentialEvent()
    data class LoadCredential(val credential: Credential) : DetailedCredentialEvent()
    object DeleteCredential : DetailedCredentialEvent()
}
