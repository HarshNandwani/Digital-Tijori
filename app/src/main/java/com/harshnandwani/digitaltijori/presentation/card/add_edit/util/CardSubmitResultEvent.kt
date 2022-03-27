package com.harshnandwani.digitaltijori.presentation.card.add_edit.util

sealed class CardSubmitResultEvent {
    object LinkCardWithIssuer : CardSubmitResultEvent()
    data class ShowError(val message: String) : CardSubmitResultEvent()
    object CardSaved : CardSubmitResultEvent()
}
