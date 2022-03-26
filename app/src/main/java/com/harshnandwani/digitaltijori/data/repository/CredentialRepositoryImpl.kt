package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.CredentialDao
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow

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
        dao.add(credential)
    }

    override fun getAll(): Flow<List<Credential>> {
        return dao.getAll()
    }

    override suspend fun get(id: Int): Credential? {
        return dao.get(id)
    }

    override fun getAllCredentialsWithEntityDetails(): Flow<Map<Company, List<Credential>>> {
        return dao.getAllCredentialsWithEntityDetails()
    }

    override suspend fun update(credential: Credential) {
        dao.update(credential)
    }

    override suspend fun delete(credential: Credential) {
        dao.delete(credential)
    }
}
