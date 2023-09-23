package com.harshnandwani.digitaltijori.data.local.dao

import androidx.room.*
import com.harshnandwani.digitaltijori.data.local.entity.CardEntity
import com.harshnandwani.digitaltijori.data.local.entity.CompanyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert
    suspend fun add(card: CardEntity)

    @Query("SELECT * FROM Card")
    fun getAll(): Flow<List<CardEntity>>

    @Query("SELECT * FROM Card WHERE cardId = :id")
    suspend fun get(id: Int): CardEntity?

    @Transaction
    @Query("SELECT * FROM Company JOIN Card ON Company.companyId = Card.companyId")
    fun getCardsWithIssuerDetails(): Flow<Map<CompanyEntity, List<CardEntity>>>

    @Query("SELECT * FROM Card WHERE bankAccountId = :bankAccountId")
    fun getCardsLinkedToABank(bankAccountId :Int): Flow<List<CardEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM Card) AS result")
    suspend fun dataExists(): Boolean

    @Update
    suspend fun update(card: CardEntity)

    @Delete
    suspend fun delete(card: CardEntity)
}
