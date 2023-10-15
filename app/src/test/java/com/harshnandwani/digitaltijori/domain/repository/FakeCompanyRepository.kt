package com.harshnandwani.digitaltijori.domain.repository

import com.harshnandwani.digitaltijori.domain.model.Company
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCompanyRepository : CompanyRepository {

    private val companies = mutableListOf<Company>()

    override suspend fun add(company: Company) {
        companies.add(company)
    }

    override suspend fun get(id: Int): Company? {
        return companies.find { it.companyId == id }
    }

    override suspend fun getAll(): List<Company> {
        return companies.toList()
    }

    override fun getAllBanks(): Flow<List<Company>> {
        return flow { emit(companies.filter { it.isABank }) }
    }

    override fun getAllCardIssuers(): Flow<List<Company>> {
        return flow { emit(companies.filter { it.issuesCards }) }
    }

    override fun getAllCompaniesWithCredentials(): Flow<List<Company>> {
        return flow { emit(companies.filter { it.hasCredentials }) }
    }

    override suspend fun update(company: Company) {
        companies.forEachIndexed { index, currentCompany ->
            if (currentCompany.companyId == company.companyId) {
                companies[index] = company
                return@forEachIndexed
            }
        }
    }
}
