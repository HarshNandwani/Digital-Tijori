package com.harshnandwani.digitaltijori.domain.use_case.card

import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.util.CardType
import com.harshnandwani.digitaltijori.domain.util.InvalidCardException
import kotlin.jvm.Throws

class ValidateCardUseCase(
    private val getCardNumberLengthByNetwork: GetCardNumberLengthUseCase
) {
    @Throws(InvalidCardException::class)
    operator fun invoke(card: Card): Boolean {
        card.apply {
            if (company.companyId <= 0) {
                throw InvalidCardException("Please link card with issuer")
            }
            if (isLinkedToBank && bankAccount == null) {
                throw InvalidCardException("Please link card with bank")
            }
            if (cardNumber.isEmpty() || cardNumber.length < getCardNumberLengthByNetwork(cardNetwork, true)) {
                throw InvalidCardException("Enter full card number")
            }
            if (expiryMonth < 1 || expiryMonth > 12) {
                throw InvalidCardException("Enter valid expiry month")
            }
            if (expiryYear.toString().length < 2) {
                throw InvalidCardException("Enter valid expiry year")
            }
            if (cvv.length != 3) {
                throw InvalidCardException("Enter valid cvv")
            }
            if (nameOnCard.isEmpty() || nameOnCard.length < 3) {
                throw InvalidCardException("Enter full name")
            }
            if (pin.length != 4) {
                throw InvalidCardException("Enter valid pin")
            }
            if (cardType == CardType.None) {
                throw InvalidCardException("Select card type Credit/Debit/Other")
            }
            return true
        }
    }
}
