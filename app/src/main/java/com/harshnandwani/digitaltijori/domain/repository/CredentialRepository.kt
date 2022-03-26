package com.harshnandwani.digitaltijori.domain.repository

import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import kotlinx.coroutines.flow.Flow

interface CredentialRepository {

    suspend fun add(credential : Credential)

    fun getAll(): Flow<List<Credential>>

    suspend fun get(id: Int): Credential?

    fun getAllCredentialsWithEntityDetails(): Flow<Map<Company, List<Credential>>>

    fun getCredentialsLinkedToAccount(bankAccountId: Int): Flow<List<Credential>>

    suspend fun update(credential: Credential)

    suspend fun delete(credential: Credential)

}
