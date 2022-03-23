package com.harshnandwani.digitaltijori.domain.use_case.card

import com.harshnandwani.digitaltijori.domain.util.CardNetwork

class IdentifyCardNetworkUseCase {

    operator fun invoke(cardNumber: String): CardNetwork {
        val visaRegex = Regex("^4[0-9]{0,}\$")
        val masterCardRegex = Regex("^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[01]|2720)[0-9]{0,}\$")
        val rupayRegex = Regex("^6(?!52[12])(?:011|5[0-9][0-9])[0-9]{12}\$")
        val amexRegex = Regex("^3[47][0-9]{0,}\$")
        val dinersClubRegex = Regex("^3(?:0[0-59]{1}|[689])[0-9]{0,}\$")

        val trimmedCardNumber = cardNumber.replace(" ", "")

        return when {
            trimmedCardNumber.matches(visaRegex) -> CardNetwork.Visa
            trimmedCardNumber.matches(masterCardRegex) -> CardNetwork.MasterCard
            trimmedCardNumber.matches(rupayRegex) -> CardNetwork.Rupay
            trimmedCardNumber.matches(amexRegex) -> CardNetwork.AmEx
            trimmedCardNumber.matches(dinersClubRegex) -> CardNetwork.DinersClub
            else -> CardNetwork.Unknown
        }
    }

}
