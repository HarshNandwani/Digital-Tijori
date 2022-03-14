package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.BankAccountDao
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import kotlinx.coroutines.flow.Flow

/*
* This may seem same as the dao,
* it might not make sense to create a repository like this
* but it is needed to better structure the project.
* in case we need to add a new datasource in future
* This repository will then contain more data logic
* and will act as single source of truth
* */
class BankAccountRepositoryImpl(private val dao: BankAccountDao): BankAccountRepository {

    override suspend fun add(account: BankAccount) {
        dao.add(account)
    }

    override fun getAll(): Flow<List<BankAccount>> {
        return dao.getAll()
    }

    override suspend fun get(id: Int): BankAccount? {
        return dao.get(id)
    }

    override fun getAccountsWithBankDetails(): Flow<Map<Company, BankAccount>> {
        return dao.getAccountsWithBankDetails()
    }

    override suspend fun update(account: BankAccount) {
        dao.update(account)
    }

    override suspend fun delete(account: BankAccount) {
        dao.delete(account)
    }
}
