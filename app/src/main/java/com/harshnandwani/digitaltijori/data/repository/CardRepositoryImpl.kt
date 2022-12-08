package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.dao.CardDao
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow

/*
* This may seem same as the dao,
* it might not make sense to create a repository like this
* but it is needed to better structure the project.
* in case we need to add a new datasource in future
* This repository will then contain more data logic
* and will act as single source of truth
* */
class CardRepositoryImpl(private val dao: CardDao): CardRepository {

    override suspend fun add(card: Card) {
        dao.add(card)
    }

    override fun getAll(): Flow<List<Card>> {
        return dao.getAll()
    }

    override suspend fun get(id: Int): Card? {
        return dao.get(id)
    }

    override fun getCardsWithIssuerDetails(): Flow<Map<Company, List<Card>>> {
        return dao.getCardsWithIssuerDetails()
    }

    override fun getCardsLinkedToABank(bankAccountId: Int): Flow<List<Card>> {
        return dao.getCardsLinkedToABank(bankAccountId)
    }

    override suspend fun update(card: Card) {
        dao.update(card)
    }

    override suspend fun delete(card: Card) {
        dao.delete(card)
    }
}
