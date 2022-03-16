package com.harshnandwani.digitaltijori.presentation.card.add_edit.util

import com.harshnandwani.digitaltijori.domain.model.Company

sealed class CardEvent {
    data class SelectIssuer(val bank: Company): CardEvent()
}
