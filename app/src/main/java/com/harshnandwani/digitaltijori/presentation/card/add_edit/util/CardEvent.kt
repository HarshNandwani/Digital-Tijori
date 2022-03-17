package com.harshnandwani.digitaltijori.presentation.card.add_edit.util

import com.harshnandwani.digitaltijori.domain.model.Company

sealed class CardEvent {
    data class SelectIssuer(val issuer: Company) : CardEvent()
    data class EnteredCardNumber(val cardNumber: String) : CardEvent()
    data class EnteredNameOnCard(val name: String) : CardEvent()
    data class EnteredCardExpiry(val expiry: String) : CardEvent()
    data class EnteredCvv(val cvv: String) : CardEvent()
    object CardSubmit : CardEvent()
}
