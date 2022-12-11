package com.harshnandwani.digitaltijori.domain.model

import com.harshnandwani.digitaltijori.domain.util.InvalidCredentialException
import java.io.Serializable
import kotlin.jvm.Throws

data class Credential(
    val credentialId: Int = 0,
    val username: String,
    val password: String,
    val isLinkedToBank : Boolean,
    val bankAccount: BankAccount?,
    val company: Company,
    val alias: String?
) : Serializable {

    @Throws(InvalidCredentialException::class)
    fun isValid(): Boolean {
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
