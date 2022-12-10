package com.harshnandwani.digitaltijori.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.harshnandwani.digitaltijori.data.local.entity.CompanyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {

    @Insert
    suspend fun add(company: CompanyEntity)

    @Query("SELECT * FROM Company")
    suspend fun getAll(): List<CompanyEntity>

    @Query("SELECT * FROM Company WHERE isABank = 1")
    fun getAllBanks(): Flow<List<CompanyEntity>>

    @Query("SELECT * FROM Company WHERE issuesCards = 1")
    fun getAllCardIssuers(): Flow<List<CompanyEntity>>

    @Query("SELECT * FROM Company WHERE hasCredentials = 1")
    fun getAllCompaniesWithCredentials(): Flow<List<CompanyEntity>>

    @Update
    suspend fun update(company: CompanyEntity)

}
