package com.harshnandwani.digitaltijori.data.local

import androidx.room.*
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import kotlinx.coroutines.flow.Flow

@Dao
interface BankAccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(account: BankAccount): Long

    @Query("SELECT * FROM BankAccount ORDER BY bankAccountId ASC")
    fun getAll() : Flow<List<BankAccount>>

    @Query("SELECT * FROM BankAccount WHERE bankAccountId = :id")
    suspend fun get(id: Int) : BankAccount?

    @Transaction
    @Query("SELECT * FROM Company JOIN BankAccount ON Company.companyId = BankAccount.companyId")
    fun getAccountsWithBankDetails(): Flow<Map<Company, List<BankAccount>>>

    @Update
    suspend fun update(account: BankAccount)

    @Delete
    suspend fun delete(account: BankAccount)
}
