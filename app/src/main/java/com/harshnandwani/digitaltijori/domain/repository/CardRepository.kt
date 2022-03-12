package com.harshnandwani.digitaltijori.domain.repository

import com.harshnandwani.digitaltijori.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    suspend fun add(card: Card)

    fun getAll(): Flow<List<Card>>

    fun get(id: Int): Card?

    fun getCardsLinkedToABank(bankAccountId :Int): Flow<List<Card>>?

    suspend fun update(card: Card)

    suspend fun delete(card: Card)

}
