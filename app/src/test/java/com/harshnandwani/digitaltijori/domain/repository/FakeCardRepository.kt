package com.harshnandwani.digitaltijori.domain.repository

import com.harshnandwani.digitaltijori.domain.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCardRepository : CardRepository {

    private val cards = mutableListOf<Card>()

    override suspend fun add(card: Card) {
        cards.add(card)
    }

    override suspend fun get(id: Int): Card? {
        return cards.find { it.cardId == id }
    }

    override fun getAll(): Flow<List<Card>> {
        return flow { emit(cards.toList()) }
    }

    override fun getCardsLinkedToABank(bankAccountId: Int): Flow<List<Card>> {
        return flow { emit(cards.filter { it.bankAccount?.bankAccountId == bankAccountId }.toList()) }
    }

    override suspend fun update(card: Card) {
        cards.forEachIndexed { index, currentCard ->
            if (currentCard.cardId == card.cardId) {
                cards[index] = card
                return@forEachIndexed
            }
        }
    }

    override suspend fun delete(card: Card) {
        cards.remove(card)
    }

    override suspend fun dataExists(): Boolean {
        return cards.size > 0
    }
}
