package com.harshnandwani.digitaltijori.presentation.card.add_edit

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.domain.util.CardType
import com.harshnandwani.digitaltijori.presentation.card.FlipCardLayout
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardEvent
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardSubmitResultEvent
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField
import com.harshnandwani.digitaltijori.presentation.company.CompaniesList
import com.harshnandwani.digitaltijori.presentation.util.CardHelperFunctions
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AddEditCardScreen(viewModel: AddEditCardViewModel) {

    val state = viewModel.state.value
    val context = LocalContext.current
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
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RadioButton(
                    selected = state.cardType == CardType.DebitCard,
                    onClick = { viewModel.onEvent(CardEvent.SelectedCardType(CardType.DebitCard)) }
                )
                Text(text = "Debit")
                RadioButton(
                    selected = state.cardType == CardType.CreditCard,
                    onClick = { viewModel.onEvent(CardEvent.SelectedCardType(CardType.CreditCard)) }
                )
                Text(text = "Credit")
                RadioButton(
                    selected = state.cardType == CardType.Other,
                    onClick = { viewModel.onEvent(CardEvent.SelectedCardType(CardType.Other)) }
                )
                Text(text = "Other")
            }

            InputTextField(
                label = "Card Number",
                value = state.cardNumber,
                onValueChange = { viewModel.onEvent(CardEvent.EnteredCardNumber(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = { number ->
                    CardHelperFunctions.formatCardNumber(state.cardNetwork, number)
                }
            )

            InputTextField(
                label = "Expiry",
                value = state.expiryMonth + state.expiryYear,
                onValueChange = { viewModel.onEvent(CardEvent.EnteredCardExpiry(it)) },
                placeholder = "mm/yy",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = { CardHelperFunctions.formatExpiry(it) }
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

            TextButton(
                onClick = { viewModel.onEvent(CardEvent.CardSubmit) }
            ) {
                Text(text = "Submit")
            }

        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CardSubmitResultEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                CardSubmitResultEvent.CardSaved -> {
                    Toast.makeText(context, "Credentials saved!", Toast.LENGTH_SHORT).show()
                    (context as AddEditCardActivity).onBackPressed()
                }
            }
        }
    }

}
