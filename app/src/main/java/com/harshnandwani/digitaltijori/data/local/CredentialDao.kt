package com.harshnandwani.digitaltijori.data.local

import androidx.room.*
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential
import kotlinx.coroutines.flow.Flow

@Dao
interface CredentialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(credential : Credential)

    @Query("SELECT * FROM Credential")
    fun getAll(): Flow<List<Credential>>

    @Query("SELECT * FROM Credential WHERE id = :id")
    fun get(id: Int): Credential?

    @Query("SELECT * FROM Company JOIN Credential ON Company.id = Credential.companyId")
    fun getAllCredentialsWithEntityDetails(): Flow<Map<Company, Credential>>

    @Update
    suspend fun update(credential: Credential)

    @Delete
    suspend fun delete(credential: Credential)
}
