package com.harshnandwani.digitaltijori.domain.use_case.card

import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow

class GetCardsLinkedToAccountUseCase(
    private val repository: CardRepository
) {
    operator fun invoke(accountId: Int): Flow<List<Card>> {
        return repository.getCardsLinkedToABank(accountId)
    }
}
