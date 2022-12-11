package com.harshnandwani.digitaltijori.presentation.card.add_edit.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.util.CardType
import com.harshnandwani.digitaltijori.domain.util.ColorScheme

sealed class CardEvent {
    data class SelectIssuer(val issuer: Company) : CardEvent()
    data class LinkToAccount(val account: BankAccount) : CardEvent()
    data class SelectedColorScheme(val colorScheme: ColorScheme) : CardEvent()
    data class SelectedCardType(val cardType: CardType) : CardEvent()
    data class EnteredCardNumber(val cardNumber: String) : CardEvent()
    data class EnteredNameOnCard(val name: String) : CardEvent()
    data class EnteredCardExpiry(val expiry: String) : CardEvent()
    data class EnteredCvv(val cvv: String) : CardEvent()
    data class EnteredVariant(val variant: String) : CardEvent()
    data class EnteredPin(val pin: String) : CardEvent()
    data class EnteredCardAlias(val alias: String) : CardEvent()
    object CardSubmit : CardEvent()
    data class ChangeToEditMode(val issuer: Company, val card: Card) : CardEvent()
}
