package com.harshnandwani.digitaltijori.presentation.credential.detailed_view.util

import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

data class DetailedCredentialState(
    val entity: Company = Company(-1, "", false, false, false, -1, -1),
    val credential: Credential = Credential.emptyCredential()
)
