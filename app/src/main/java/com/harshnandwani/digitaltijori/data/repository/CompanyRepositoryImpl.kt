package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.dao.CompanyDao
import com.harshnandwani.digitaltijori.data.local.entity.CompanyEntity
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/*
* This may seem same as the dao,
* it might not make sense to create a repository like this
* but it is needed to better structure the project.
* in case we need to add a new datasource in future
* This repository will then contain more data logic
* and will act as single source of truth
* */
class CompanyRepositoryImpl(private val dao: CompanyDao): CompanyRepository {

    override suspend fun add(company: Company) {
        dao.add(CompanyEntity.toEntity(company))
    }

    override suspend fun getAll(): List<Company> {
        return dao.getAll().map { it.toDomain() }
    }

    override fun getAllBanks(): Flow<List<Company>> {
        return dao.getAllBanks().map { it.map { it.toDomain() } }
    }

    override fun getAllCardIssuers(): Flow<List<Company>> {
        return dao.getAllCardIssuers().map { it.map { it.toDomain() } }
    }

    override fun getAllCompaniesWithCredentials(): Flow<List<Company>> {
        return dao.getAllCompaniesWithCredentials().map { it.map { it.toDomain() } }
    }

    override suspend fun update(company: Company) {
        dao.update(CompanyEntity.toEntity(company))
    }
}
