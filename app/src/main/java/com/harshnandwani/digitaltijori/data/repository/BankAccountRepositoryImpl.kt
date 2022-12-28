package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.dao.BankAccountDao
import com.harshnandwani.digitaltijori.data.local.entity.BankAccountEntity
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.*

class BankAccountRepositoryImpl(
    private val dao: BankAccountDao,
    private val companyRepository: CompanyRepository
): BankAccountRepository {

    override suspend fun add(account: BankAccount): Long {
        return dao.add(BankAccountEntity.toEntity(account))
    }

    override suspend fun get(id: Int): BankAccount? {
        val account = dao.get(id) ?: return null
        val company = companyRepository.get(account.companyId) ?: return null
        return account.toDomain(company)
    }

    override fun getAll(): Flow<List<BankAccount>> {
        //TODO: Try to convert below flow transformation to util function
        return dao.getAccountsWithBankDetails().transform {
            val result = it.flatMap { entry ->
                entry.value.map { account -> account.toDomain(entry.key.toDomain()) }
            }
            emit(result)
        }
    }

    override suspend fun update(account: BankAccount) {
        dao.update(BankAccountEntity.toEntity(account))
    }

    override suspend fun delete(account: BankAccount) {
        dao.delete(BankAccountEntity.toEntity(account))
    }
}
