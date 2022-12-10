package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.dao.CardDao
import com.harshnandwani.digitaltijori.data.local.entity.CardEntity
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

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
        dao.add(CardEntity.toEntity(card))
    }

    override fun getAll(): Flow<List<Card>> {
        return dao.getAll().map { it.map { card -> card.toDomain() } }
    }

    override suspend fun get(id: Int): Card? {
        return dao.get(id)?.toDomain()
    }

    override fun getCardsWithIssuerDetails(): Flow<Map<Company, List<Card>>> = flow {
        dao.getCardsWithIssuerDetails().onEach {
            it.entries.forEach {
                emit(
                    mapOf(
                        Pair(it.key.toDomain(),
                            it.value.map { it.toDomain() })
                    )
                )
            }
        }
    }

    override fun getCardsLinkedToABank(bankAccountId: Int): Flow<List<Card>> {
        return dao.getCardsLinkedToABank(bankAccountId).map { it.map { card -> card.toDomain() } }
    }

    override suspend fun update(card: Card) {
        dao.update(CardEntity.toEntity(card))
    }

    override suspend fun delete(card: Card) {
        dao.delete(CardEntity.toEntity(card))
    }
}
