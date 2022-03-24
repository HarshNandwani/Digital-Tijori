package com.harshnandwani.digitaltijori.data.local

import androidx.room.*
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert
    suspend fun add(card: Card)

    @Query("SELECT * FROM Card")
    fun getAll(): Flow<List<Card>>

    @Query("SELECT * FROM Card WHERE id = :id")
    suspend fun get(id: Int): Card?

    @Transaction
    @Query("SELECT * FROM Company JOIN Card ON Company.id = Card.companyId")
    fun getCardsWithIssuerDetails(): Flow<Map<Company, Card>>

    @Query("SELECT * FROM Card WHERE bankAccountId = :bankAccountId")
    fun getCardsLinkedToABank(bankAccountId :Int): Flow<List<Card>>?

    @Update
    suspend fun update(card: Card)

    @Delete
    suspend fun delete(card: Card)
}
