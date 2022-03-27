package com.harshnandwani.digitaltijori.domain.util

enum class CardType {
    Debit,
    Credit,
    Other,
    None
}

val cardsTypesList = listOf(
    CardType.Debit,
    CardType.Credit,
    CardType.Other
)
