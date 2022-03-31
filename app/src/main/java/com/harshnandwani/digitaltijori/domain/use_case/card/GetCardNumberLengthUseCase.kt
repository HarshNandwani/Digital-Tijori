package com.harshnandwani.digitaltijori.domain.use_case.card

import com.harshnandwani.digitaltijori.domain.util.CardNetwork

class GetCardNumberLengthUseCase {

    /*
    * Returns Length of card number for the passes Card Network
    * By default returns Max card number
    * if [getMinLength] is true then returns min card size
    * */
    operator fun invoke(cardNetwork: CardNetwork, getMinLength: Boolean = false): Byte {
        return when (cardNetwork) {
            CardNetwork.Visa -> if (getMinLength) 13 else 16
            CardNetwork.MasterCard -> 16
            CardNetwork.Rupay -> 16
            CardNetwork.AmEx -> 15
            CardNetwork.DinersClub -> if (getMinLength) 14 else 19
            CardNetwork.Unknown -> if (getMinLength) 13 else 19 // according to: https://en.wikipedia.org/wiki/Payment_card_number
        }
    }

}
