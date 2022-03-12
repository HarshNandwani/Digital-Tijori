package com.harshnandwani.digitaltijori.domain.model

import com.harshnandwani.digitaltijori.domain.util.CardType

data class Card(
    val id : Int,
    val isLinkedToBank : Boolean,
    val bankAccountId : Int?,
    val cardNumber : String,
    val expiryMonth : Byte,
    val expiryYear: Byte,
    val cvv : Short,
    val nameOnCard : String,
    val cardNetwork: String,
    val cardAlias : String?,
    val cardType: CardType,
)
