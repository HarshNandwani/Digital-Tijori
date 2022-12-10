package com.harshnandwani.digitaltijori.data.local.dao

import androidx.room.*
import com.harshnandwani.digitaltijori.data.local.entity.BankAccountEntity
import com.harshnandwani.digitaltijori.data.local.entity.CompanyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BankAccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(account: BankAccountEntity): Long

    @Query("SELECT * FROM BankAccount ORDER BY bankAccountId ASC")
    fun getAll() : Flow<List<BankAccountEntity>>

    @Query("SELECT * FROM BankAccount WHERE bankAccountId = :id")
    suspend fun get(id: Int) : BankAccountEntity?

    @Transaction
    @Query("SELECT * FROM Company JOIN BankAccount ON Company.companyId = BankAccount.companyId")
    fun getAccountsWithBankDetails(): Flow<Map<CompanyEntity, List<BankAccountEntity>>>

    @Update
    suspend fun update(account: BankAccountEntity)

    @Delete
    suspend fun delete(account: BankAccountEntity)
}
