package com.harshnandwani.digitaltijori.data.local

import androidx.room.*
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import kotlinx.coroutines.flow.Flow

@Dao
interface BankAccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(account: BankAccount)

    @Query("SELECT * FROM BankAccount ORDER BY id ASC")
    fun getAll() : Flow<List<BankAccount>>

    @Query("SELECT * FROM BankAccount WHERE id = :id")
    suspend fun get(id: Int) : BankAccount?

    @Transaction
    @Query("SELECT * FROM Company JOIN BANKACCOUNT ON Company.id = BankAccount.bankId")
    fun getAccountsWithBankDetails(): Flow<Map<Company, BankAccount>>


    @Update
    suspend fun update(account: BankAccount)

    @Delete
    suspend fun delete(account: BankAccount)
}
