package com.harshnandwani.digitaltijori.data.local

import androidx.room.*
import com.harshnandwani.digitaltijori.domain.model.Card
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert
    suspend fun add(card: Card)

    @Query("SELECT * FROM Card")
    fun getAll(): Flow<List<Card>>

    @Query("SELECT * FROM Card WHERE id = :id")
    fun get(id: Int): Card?

    @Query("SELECT * FROM Card WHERE bankAccountId = :bankAccountId")
    fun getCardsLinkedToABank(bankAccountId :Int): List<Card>?

    @Update
    suspend fun update(card: Card)

    @Delete
    suspend fun delete(card: Card)
}
