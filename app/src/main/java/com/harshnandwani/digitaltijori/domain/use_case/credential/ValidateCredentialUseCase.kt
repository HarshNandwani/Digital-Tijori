package com.harshnandwani.digitaltijori.domain.use_case.credential

import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.util.InvalidCredentialException

class ValidateCredentialUseCase {
    operator fun invoke(credential: Credential): Boolean {
        credential.apply {
            if (company.companyId <= 0) {
                throw InvalidCredentialException("Select entity")
            }
            if (isLinkedToBank && bankAccount == null) {
                throw InvalidCredentialException("Please link credential with bank")
            }
            if (username.isEmpty()) {
                throw InvalidCredentialException("Enter username")
            }
            if (password.isEmpty()) {
                throw InvalidCredentialException("Enter password")
            }
            return true
        }
    }
}
