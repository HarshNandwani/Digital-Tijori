package com.harshnandwani.digitaltijori.presentation.card.add_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.presentation.card.CardLayout
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardEvent
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField

@ExperimentalComposeUiApi
@Composable
fun AddEditCardScreen(viewModel: AddEditCardViewModel) {

    val state = viewModel.state.value

    Column(
        modifier = Modifier.padding(24.dp)
    ) {

        CardLayout(
            variant = "",
            company = state.selectedIssuer,
            nameText = state.nameOnCard,
            cardNumber = state.cardNumber,
            expiryNumber = state.expiryMonth + state.expiryYear,
            cvvNumber = state.cvv,
            cardNetwork = state.cardNetwork
        )

        InputTextField(
            label = "Card Number",
            value = state.cardNumber,
            onValueChange = { viewModel.onEvent(CardEvent.EnteredCardNumber(it)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        InputTextField(
            label = "Card Expiry",
            value = state.expiryMonth + state.expiryYear,
            onValueChange = { viewModel.onEvent(CardEvent.EnteredCardExpiry(it)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        InputTextField(
            label = "CVV",
            value = state.cvv,
            onValueChange = { viewModel.onEvent(CardEvent.EnteredCvv(it)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        InputTextField(
            label = "Holder Name",
            value = state.nameOnCard,
            onValueChange = { viewModel.onEvent(CardEvent.EnteredNameOnCard(it)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

    }

}
