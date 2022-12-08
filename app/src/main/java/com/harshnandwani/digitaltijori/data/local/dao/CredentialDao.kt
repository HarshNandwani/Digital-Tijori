package com.harshnandwani.digitaltijori.data.local.dao

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

    @Query("SELECT * FROM Credential WHERE credentialId = :id")
    suspend fun get(id: Int): Credential?

    @Query("SELECT * FROM Company JOIN Credential ON Company.companyId = Credential.companyId")
    fun getAllCredentialsWithEntityDetails(): Flow<Map<Company, List<Credential>>>

    @Query("SELECT * FROM Credential WHERE bankAccountId = :bankAccountId")
    fun getCredentialsLinkedToAccount(bankAccountId: Int): Flow<List<Credential>>

    @Update
    suspend fun update(credential: Credential)

    @Delete
    suspend fun delete(credential: Credential)
}
