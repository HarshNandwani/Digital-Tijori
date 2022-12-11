package com.harshnandwani.digitaltijori.domain.model

import com.harshnandwani.digitaltijori.domain.use_case.card.GetCardNumberLengthUseCase
import com.harshnandwani.digitaltijori.domain.util.ColorScheme
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.CardType
import com.harshnandwani.digitaltijori.domain.util.InvalidCardException
import java.io.Serializable
import kotlin.jvm.Throws

data class Card(
    val cardId: Int = 0,
    val isLinkedToBank: Boolean,
    val bankAccountId: Int?,
    val companyId: Int?,
    val cardNumber: String,
    val expiryMonth: Byte,
    val expiryYear: Byte,
    val cvv: String,
    val nameOnCard: String,
    val variant: String?,
    val cardNetwork: CardNetwork,
    val pin: String,
    val colorScheme: ColorScheme = ColorScheme.DEFAULT,
    val cardAlias: String?,
    val cardType: CardType,
) : Serializable {

    companion object {

        fun emptyCard(): Card {
            return Card(
                0,
                false,
                null,
                null,
                "",
                14,
                1,
                "",
                "",
                null,
                CardNetwork.Unknown,
                "",
                ColorScheme.DEFAULT,
                null,
                CardType.None
            )
        }

    }

    @Throws(InvalidCardException::class)
    fun isValidCard(): Boolean {
        if (bankAccountId == null && companyId == null) {
            throw InvalidCardException("Please link card with issuer")
        }
        if (cardType == CardType.None) {
            throw InvalidCardException("Select card type Credit/Debit/Other")
        }
        if (cardNumber.isEmpty() ||
            cardNumber.length < GetCardNumberLengthUseCase().invoke(cardNetwork, true)
        ) {
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
        return true
    }
}
