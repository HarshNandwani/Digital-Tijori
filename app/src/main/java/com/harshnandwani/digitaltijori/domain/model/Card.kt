package com.harshnandwani.digitaltijori.domain.model

import com.harshnandwani.digitaltijori.domain.util.ColorScheme
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.CardType
import java.io.Serializable

data class Card(
    val cardId: Int = 0,
    val isLinkedToBank: Boolean,
    val bankAccount: BankAccount?,
    val company: Company,
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
) : Serializable
