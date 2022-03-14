package com.harshnandwani.digitaltijori.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.harshnandwani.digitaltijori.domain.model.Company
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {

    @Insert
    suspend fun add(company: Company)

    @Query("SELECT * FROM Company")
    fun getAll(): Flow<List<Company>>

    @Query("SELECT * FROM Company WHERE isABank = 1")
    fun getAllBanks(): Flow<List<Company>>

    @Query("SELECT * FROM Company WHERE issuesCards = 1")
    fun getAllCardIssuers(): Flow<List<Company>>

    @Query("SELECT * FROM Company WHERE hasCredentials = 1")
    fun getAllCompaniesWithCredentials(): Flow<List<Company>>

    @Update
    suspend fun update(company: Company)

}
