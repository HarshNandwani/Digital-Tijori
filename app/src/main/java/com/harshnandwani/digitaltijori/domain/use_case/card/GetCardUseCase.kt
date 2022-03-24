package com.harshnandwani.digitaltijori.domain.use_case.card

import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.repository.CardRepository

class GetCardUseCase(
    private val repository: CardRepository
) {

    suspend operator fun invoke(id: Int): Card? {
        return repository.get(id)
    }

}
