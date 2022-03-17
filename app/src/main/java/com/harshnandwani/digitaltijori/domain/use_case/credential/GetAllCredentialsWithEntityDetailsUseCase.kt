package com.harshnandwani.digitaltijori.domain.use_case.credential

import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow

class GetAllCredentialsWithEntityDetailsUseCase(
    private val repository: CredentialRepository
) {

    operator fun invoke(): Flow<Map<Company, Credential>> {
        return repository.getAllCredentialsWithEntityDetails()
    }

}
