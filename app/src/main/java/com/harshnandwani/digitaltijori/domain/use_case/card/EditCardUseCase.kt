package com.harshnandwani.digitaltijori.domain.use_case.card

import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import com.harshnandwani.digitaltijori.domain.util.InvalidCardException
import kotlin.jvm.Throws

class EditCardUseCase(
    private val repository: CardRepository
) {

    @Throws(InvalidCardException::class)
    suspend operator fun invoke(card: Card) {
        if (card.isValidCard()) {
            repository.add(card)
        }
    }

}
