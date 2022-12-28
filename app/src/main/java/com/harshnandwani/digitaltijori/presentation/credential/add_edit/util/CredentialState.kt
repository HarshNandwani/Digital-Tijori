package com.harshnandwani.digitaltijori.presentation.credential.add_edit.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.presentation.util.Constants.emptyCredential
import com.harshnandwani.digitaltijori.presentation.util.Parameters

data class CredentialState(
    val allEntities: List<Company> = emptyList(),
    val selectedEntity: Company? = null,
    val mode: String = Parameters.VAL_MODE_ADD,
    val credential: MutableState<Credential> = mutableStateOf(emptyCredential),
    var previouslyEnteredCredential: Credential = emptyCredential
)
