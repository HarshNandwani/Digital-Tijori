package com.harshnandwani.digitaltijori.domain.use_case.credential

import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow

class GetCredentialsLinkedToAccountUseCase(
    private val repository: CredentialRepository
) {

    operator fun invoke(accountId: Int): Flow<List<Credential>> {
        return repository.getCredentialsLinkedToAccount(accountId)
    }

}
