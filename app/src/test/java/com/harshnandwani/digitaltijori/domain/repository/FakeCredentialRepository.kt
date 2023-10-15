package com.harshnandwani.digitaltijori.domain.repository

import com.harshnandwani.digitaltijori.domain.model.Credential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCredentialRepository : CredentialRepository {

    private val credentials = mutableListOf<Credential>()

    override suspend fun add(credential: Credential) {
        credentials.add(credential)
    }

    override suspend fun get(id: Int): Credential? {
        return credentials.find { it.credentialId == id }
    }

    override fun getAll(): Flow<List<Credential>> {
        return flow { emit(credentials.toList()) }
    }

    override fun getCredentialsLinkedToAccount(bankAccountId: Int): Flow<List<Credential>> {
        return flow {
            emit(credentials.filter { it.bankAccount?.bankAccountId == bankAccountId }.toList())
        }
    }

    override suspend fun update(credential: Credential) {
        credentials.forEachIndexed { index, currentCredential ->
            if (currentCredential.credentialId == credential.credentialId) {
                credentials[index] = credential
                return@forEachIndexed
            }
        }
    }

    override suspend fun delete(credential: Credential) {
        credentials.remove(credential)
    }

    override suspend fun dataExists(): Boolean {
        return credentials.size > 0
    }
}
