package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.dao.BankAccountDao
import com.harshnandwani.digitaltijori.data.local.entity.BankAccountEntity
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import kotlinx.coroutines.flow.*

/*
* This may seem same as the dao,
* it might not make sense to create a repository like this
* but it is needed to better structure the project.
* in case we need to add a new datasource in future
* This repository will then contain more data logic
* and will act as single source of truth
* */
class BankAccountRepositoryImpl(private val dao: BankAccountDao): BankAccountRepository {

    override suspend fun add(account: BankAccount): Long {
        return dao.add(BankAccountEntity.toEntity(account))
    }

    override fun getAll(): Flow<List<BankAccount>> {
        return dao.getAll().map { it.map { account ->  account.toDomain() } }
    }

    override suspend fun get(id: Int): BankAccount? {
        return dao.get(id)?.toDomain()
    }

    override fun getAccountsWithBankDetails(): Flow<Map<Company, List<BankAccount>>> = flow {
        dao.getAccountsWithBankDetails().onEach {
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

    override suspend fun update(account: BankAccount) {
        dao.update(BankAccountEntity.toEntity(account))
    }

    override suspend fun delete(account: BankAccount) {
        dao.delete(BankAccountEntity.toEntity(account))
    }
}
