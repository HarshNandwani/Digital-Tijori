package com.harshnandwani.digitaltijori.presentation.card.add_edit.util

sealed class CardSubmitResultEvent {
    data class ShowError(val message: String) : CardSubmitResultEvent()
    object InvalidExpiry : CardSubmitResultEvent()
    object InvalidCvv : CardSubmitResultEvent()
    object CardSaved : CardSubmitResultEvent()
}
