package com.harshnandwani.digitaltijori.domain.repository

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBankAccountRepository : BankAccountRepository {

    private val bankAccounts = mutableListOf<BankAccount>()
    private var currentId = 0

    override suspend fun add(account: BankAccount): Long {
        currentId += 1
        bankAccounts.add(account.copy(bankAccountId = currentId))
        return currentId.toLong()
    }

    override suspend fun get(id: Int): BankAccount? {
        return bankAccounts.find { it.bankAccountId == id }
    }

    override fun getAll(): Flow<List<BankAccount>> {
        return flow { emit(bankAccounts.toList()) }
    }

    override suspend fun update(account: BankAccount) {
        bankAccounts.forEachIndexed { index, currentBankAccount ->
            if (currentBankAccount.bankAccountId == account.bankAccountId) {
                bankAccounts[index] = account
                return@forEachIndexed
            }
        }
    }

    override suspend fun delete(account: BankAccount) {
        bankAccounts.remove(account)
    }

    override suspend fun dataExists(): Boolean {
        return bankAccounts.size > 0
    }
}
