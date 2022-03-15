package com.harshnandwani.digitaltijori.presentation.util

import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.domain.util.CardNetwork

fun getDrawableIdForCardNetwork(cardNetwork: CardNetwork): Int {
    return when (cardNetwork) {
        CardNetwork.Visa -> R.drawable.visa_logo
        CardNetwork.MasterCard -> R.drawable.mastercard_logo
        CardNetwork.Rupay -> R.drawable.rupay_logo
        CardNetwork.AmEx -> R.drawable.american_express_logo
        CardNetwork.DinersClub -> R.drawable.diners_club_logo
        CardNetwork.Unknown -> -1
    }
}
