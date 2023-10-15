package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.dao.CardDao
import com.harshnandwani.digitaltijori.data.local.entity.CardEntity
import com.harshnandwani.digitaltijori.data.local.entity.CompanyEntity
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.*

class CardRepositoryImpl(
    private val dao: CardDao,
    private val companyRepository: CompanyRepository,
    private val accountRepository: BankAccountRepository
) : CardRepository {

    override suspend fun add(card: Card) {
        dao.add(CardEntity.toEntity(card))
    }

    override suspend fun get(id: Int): Card? {
        val cardEntity = dao.get(id) ?: return null
        return mapEntityToDomain(cardEntity)
    }

    override fun getAll(): Flow<List<Card>> {
        return transformFlowToDomain(dao.getCardsWithIssuerDetails())
    }

    override fun getCardsLinkedToABank(bankAccountId: Int): Flow<List<Card>> {
        return dao.getCardsLinkedToABank(bankAccountId).map {
            it.mapNotNull { cardEntity -> mapEntityToDomain(cardEntity) }
        }
    }

    override suspend fun update(card: Card) {
        dao.update(CardEntity.toEntity(card))
    }

    override suspend fun delete(card: Card) {
        dao.delete(CardEntity.toEntity(card))
    }

    override suspend fun dataExists(): Boolean {
        return dao.dataExists()
    }

    private suspend fun mapEntityToDomain(cardEntity: CardEntity): Card? {
        cardEntity.bankAccountId?.let {
            val linkedAccount = accountRepository.get(it) ?: return@let
            return cardEntity.toDomain(linkedAccount, linkedAccount.linkedCompany)
        }
        cardEntity.companyId?.let {
            val linkedCompany = companyRepository.get(it) ?: return null
            return cardEntity.toDomain(null, linkedCompany)
        } ?: return null
    }

    private fun transformFlowToDomain(entityFlow: Flow<Map<CompanyEntity, List<CardEntity>>>): Flow<List<Card>> {
        return entityFlow.transform {
            val cards = it.flatMap { entry ->
                entry.value.mapNotNull { cardEntity -> mapEntityToDomain(cardEntity) }
            }
            emit(cards)
        }
    }

}
