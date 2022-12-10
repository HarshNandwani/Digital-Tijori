package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.dao.CredentialDao
import com.harshnandwani.digitaltijori.data.local.entity.CredentialEntity
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/*
* This may seem same as the dao,
* it might not make sense to create a repository like this
* but it is needed to better structure the project.
* in case we need to add a new datasource in future
* This repository will then contain more data logic
* and will act as single source of truth
* */
class CredentialRepositoryImpl(private val dao: CredentialDao): CredentialRepository {

    override suspend fun add(credential: Credential) {
        dao.add(CredentialEntity.toEntity(credential))
    }

    override fun getAll(): Flow<List<Credential>> {
        return dao.getAll().map { it.map { credentialEntity -> credentialEntity.toDomain() } }
    }

    override suspend fun get(id: Int): Credential? {
        return dao.get(id)?.toDomain()
    }

    override fun getAllCredentialsWithEntityDetails(): Flow<Map<Company, List<Credential>>> = flow {
        dao.getAllCredentialsWithEntityDetails().onEach {
            it.entries.forEach {
                emit(
                    mapOf(
                        Pair(it.key.toDomain(),
                            it.value.map { it.toDomain() })
                    )
                )
            }
        }
    }

    override fun getCredentialsLinkedToAccount(bankAccountId: Int): Flow<List<Credential>> {
        return dao.getCredentialsLinkedToAccount(bankAccountId).map { it.map { credentialEntity -> credentialEntity.toDomain() } }
    }

    override suspend fun update(credential: Credential) {
        dao.update(CredentialEntity.toEntity(credential))
    }

    override suspend fun delete(credential: Credential) {
        dao.delete(CredentialEntity.toEntity(credential))
    }
}
