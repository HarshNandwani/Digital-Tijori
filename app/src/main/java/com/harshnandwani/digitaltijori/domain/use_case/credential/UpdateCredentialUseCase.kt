package com.harshnandwani.digitaltijori.domain.use_case.credential

import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository
import com.harshnandwani.digitaltijori.domain.util.InvalidCredentialException
import kotlin.jvm.Throws

class UpdateCredentialUseCase(
    private val repository: CredentialRepository
) {

    @Throws(InvalidCredentialException::class)
    suspend operator fun invoke(credential: Credential) {
        if (credential.isValid()) {
            repository.update(credential)
        }
    }

}
