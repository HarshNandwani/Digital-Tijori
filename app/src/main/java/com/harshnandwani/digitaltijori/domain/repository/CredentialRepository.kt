package com.harshnandwani.digitaltijori.domain.repository

import com.harshnandwani.digitaltijori.domain.model.Credential
import kotlinx.coroutines.flow.Flow

interface CredentialRepository {

    suspend fun add(credential : Credential)

    suspend fun get(id: Int): Credential?

    fun getAll(): Flow<List<Credential>>

    fun getCredentialsLinkedToAccount(bankAccountId: Int): Flow<List<Credential>>

    suspend fun update(credential: Credential)

    suspend fun delete(credential: Credential)

    suspend fun dataExists(): Boolean

}
