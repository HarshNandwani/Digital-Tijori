package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.dao.CompanyDao
import com.harshnandwani.digitaltijori.data.local.entity.CompanyEntity
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CompanyRepositoryImpl(private val dao: CompanyDao): CompanyRepository {

    override suspend fun add(company: Company) {
        dao.add(CompanyEntity.toEntity(company))
    }

    override suspend fun get(id: Int): Company? {
        return dao.get(id)?.toDomain()
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
