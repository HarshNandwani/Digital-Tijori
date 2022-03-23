package com.harshnandwani.digitaltijori.presentation.card.add_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.presentation.card.FlipCardLayout
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardEvent
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField
import com.harshnandwani.digitaltijori.presentation.company.CompaniesList
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AddEditCardScreen(viewModel: AddEditCardViewModel) {

    val state = viewModel.state.value
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    ModalBottomSheetLayout(
        sheetContent = {
            CompaniesList(
                titleText = "Select card issuer",
                companies = state.allCardIssuers,
                onSelect = {
                    viewModel.onEvent(CardEvent.SelectIssuer(it))
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        },
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            FlipCardLayout(
                variant = "",
                company = state.selectedIssuer,
                nameText = state.nameOnCard,
                cardNumber = state.cardNumber,
                expiryNumber = state.expiryMonth + state.expiryYear,
                cvvNumber = state.cvv,
                cardNetwork = state.cardNetwork,
                onIssuerLogoClick = {
                    if(state.mode == Parameters.VAL_MODE_ADD) {
                        keyboardController?.hide()
                        coroutineScope.launch { bottomSheetState.show() }
                    }
                }
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

            InputTextField(
                label = "Alias for card",
                value = state.cardAlias,
                onValueChange = { viewModel.onEvent(CardEvent.EnteredCardAlias(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

        }
    }
}
