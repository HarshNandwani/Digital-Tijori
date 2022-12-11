package com.harshnandwani.digitaltijori.domain.repository

import com.harshnandwani.digitaltijori.domain.model.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    suspend fun add(company: Company)

    suspend fun get(id: Int): Company?

    suspend fun getAll(): List<Company>

    fun getAllBanks(): Flow<List<Company>>

    fun getAllCardIssuers(): Flow<List<Company>>

    fun getAllCompaniesWithCredentials(): Flow<List<Company>>

    suspend fun update(company: Company)
}
