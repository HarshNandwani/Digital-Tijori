package com.harshnandwani.digitaltijori.domain.use_case.card

import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class GetAllCardsWithIssuerDetailsUseCase(
    private val repository: CardRepository
) {
    operator fun invoke(): Flow<List<Card>> {
        return repository.getCardsWithIssuerDetails().transform {
            emit(it.filterNotNull())
        }
    }
}
