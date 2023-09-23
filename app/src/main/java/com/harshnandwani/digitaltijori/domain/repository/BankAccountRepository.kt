package com.harshnandwani.digitaltijori.domain.repository

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import kotlinx.coroutines.flow.Flow

interface BankAccountRepository {

    suspend fun add(account: BankAccount): Long

    suspend fun get(id: Int) : BankAccount?

    fun getAll(): Flow<List<BankAccount>>

    suspend fun update(account: BankAccount)

    suspend fun delete(account: BankAccount)

    suspend fun dataExists(): Boolean

}
