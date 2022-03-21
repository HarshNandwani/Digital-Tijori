package com.harshnandwani.digitaltijori.domain.use_case.credential

import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository

class GetCredentialUseCase(
    private val repository: CredentialRepository
) {

    suspend operator fun invoke(id: Int): Credential? {
        return repository.get(id)
    }

}
