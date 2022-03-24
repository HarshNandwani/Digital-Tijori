package com.harshnandwani.digitaltijori.presentation.card.add_edit.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.CardType
import com.harshnandwani.digitaltijori.presentation.util.Parameters

data class CardState(
    val allCardIssuers: List<Company> = emptyList(),
    val selectedIssuer: Company? = null,
    val mode: String = Parameters.VAL_MODE_ADD,
    val cardNumber: String = "",
    val expiryMonth: String = "",
    val expiryYear: String = "",
    val cvv: String = "",
    val nameOnCard: String = "",
    var cardNetwork: CardNetwork = CardNetwork.Unknown,
    val cardAlias: String = "",
    val cardType: CardType = CardType.None,
    val backVisible: MutableState<Boolean> = mutableStateOf(false),
    var previouslyEnteredCard: Card = Card.emptyCard()
)
