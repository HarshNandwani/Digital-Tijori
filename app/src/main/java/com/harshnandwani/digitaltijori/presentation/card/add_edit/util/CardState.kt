package com.harshnandwani.digitaltijori.presentation.card.add_edit.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.util.Constants.emptyCard
import com.harshnandwani.digitaltijori.presentation.util.Parameters

data class CardState(
    val allCardIssuers: List<Company> = emptyList(),
    val selectedIssuer: Company? = null,
    val mode: String = Parameters.VAL_MODE_ADD,
    val expiryMonth: String = "",
    val expiryYear: String = "",
    val card: MutableState<Card> = mutableStateOf(emptyCard),
    val backVisible: MutableState<Boolean> = mutableStateOf(false),
    var previouslyEnteredCard: Card = emptyCard
)
