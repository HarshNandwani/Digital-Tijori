package com.harshnandwani.digitaltijori.presentation.credential.add_edit.util

import com.harshnandwani.digitaltijori.domain.model.Company

data class CredentialState(
    val allEntities: List<Company> = emptyList()
)
