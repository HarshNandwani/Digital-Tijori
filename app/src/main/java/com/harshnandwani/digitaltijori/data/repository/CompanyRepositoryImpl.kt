package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.dao.CompanyDao
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow

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
        dao.add(company)
    }

    override suspend fun getAll(): List<Company> {
        return dao.getAll()
    }

    override fun getAllBanks(): Flow<List<Company>> {
        return dao.getAllBanks()
    }

    override fun getAllCardIssuers(): Flow<List<Company>> {
        return dao.getAllCardIssuers()
    }

    override fun getAllCompaniesWithCredentials(): Flow<List<Company>> {
        return dao.getAllCompaniesWithCredentials()
    }

    override suspend fun update(company: Company) {
        dao.update(company)
    }
}
