package com.harshnandwani.digitaltijori.presentation.card.add_edit.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.util.Parameters

data class CardState(
    val allCardIssuers: List<Company> = emptyList(),
    val selectedIssuer: Company? = null,
    val mode: String = Parameters.VAL_MODE_ADD,
    val card: MutableState<Card> = mutableStateOf(Card.emptyCard()),
    var previouslyEnteredCard: Card = Card.emptyCard()
)
