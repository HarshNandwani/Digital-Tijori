package com.harshnandwani.digitaltijori.data.local.dao

import androidx.room.*
import com.harshnandwani.digitaltijori.data.local.entity.CompanyEntity
import com.harshnandwani.digitaltijori.data.local.entity.CredentialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CredentialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(credential : CredentialEntity)

    @Query("SELECT * FROM Credential")
    fun getAll(): Flow<List<CredentialEntity>>

    @Query("SELECT * FROM Credential WHERE credentialId = :id")
    suspend fun get(id: Int): CredentialEntity?

    @Query("SELECT * FROM Company JOIN Credential ON Company.companyId = Credential.companyId")
    fun getAllCredentialsWithEntityDetails(): Flow<Map<CompanyEntity, List<CredentialEntity>>>

    @Query("SELECT * FROM Credential WHERE bankAccountId = :bankAccountId")
    fun getCredentialsLinkedToAccount(bankAccountId: Int): Flow<List<CredentialEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM Credential) AS result")
    suspend fun dataExists(): Boolean

    @Update
    suspend fun update(credential: CredentialEntity)

    @Delete
    suspend fun delete(credential: CredentialEntity)
}
