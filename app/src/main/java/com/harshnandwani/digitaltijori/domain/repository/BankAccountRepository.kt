package com.harshnandwani.digitaltijori.domain.repository

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import kotlinx.coroutines.flow.Flow

interface BankAccountRepository {

    suspend fun add(account: BankAccount)

    fun getAll() : Flow<List<BankAccount>>

    suspend fun get(id: Int) : BankAccount?

    fun getAccountsWithBankDetails(): Flow<Map<Company, BankAccount>>

    suspend fun update(account: BankAccount)

    suspend fun delete(account: BankAccount)

}
