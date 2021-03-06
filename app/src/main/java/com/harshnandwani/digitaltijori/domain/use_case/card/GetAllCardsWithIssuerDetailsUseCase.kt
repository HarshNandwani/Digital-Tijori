package com.harshnandwani.digitaltijori.domain.use_case.card

import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow

class GetAllCardsWithIssuerDetailsUseCase(
    private val repository: CardRepository
) {

    operator fun invoke(): Flow<Map<Company, List<Card>>> {
        return repository.getCardsWithIssuerDetails()
    }

}
