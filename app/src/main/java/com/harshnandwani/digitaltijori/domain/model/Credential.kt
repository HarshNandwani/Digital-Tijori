package com.harshnandwani.digitaltijori.domain.model

import com.harshnandwani.digitaltijori.domain.util.InvalidCredentialException
import java.io.Serializable
import kotlin.jvm.Throws

data class Credential(
    val credentialId: Int = 0,
    val username: String,
    val password: String,
    val isLinkedToBank : Boolean,
    val bankAccountId : Int?,
    val companyId: Int?,
    val alias: String?
) : Serializable {

    companion object {
        fun emptyCredential(): Credential {
            return Credential(
                username = "",
                password = "",
                isLinkedToBank = false,
                bankAccountId = null,
                companyId = null,
                alias = null
            )
        }
    }

    @Throws(InvalidCredentialException::class)
    fun isValid(): Boolean {
        if (isLinkedToBank && (bankAccountId == null || bankAccountId == -1)) {
            throw InvalidCredentialException("Select entity")
        }
        if (!isLinkedToBank && (companyId == null || companyId == -1)) {
            throw InvalidCredentialException("Select entity")
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
