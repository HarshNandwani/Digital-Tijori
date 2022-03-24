package com.harshnandwani.digitaltijori.domain.use_case.card

import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.repository.CardRepository

class DeleteCardUseCase(
    private val repository: CardRepository
) {

    suspend operator fun invoke(card: Card) {
        repository.delete(card)
    }

}
