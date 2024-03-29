package com.harshnandwani.digitaltijori.presentation.card.add_edit

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harshnandwani.digitaltijori.domain.util.CardType
import com.harshnandwani.digitaltijori.domain.util.cardsTypesList
import com.harshnandwani.digitaltijori.presentation.card.FlipCardLayout
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardEvent
import com.harshnandwani.digitaltijori.presentation.card.add_edit.util.CardSubmitResultEvent
import com.harshnandwani.digitaltijori.presentation.common_components.BottomSheetLayout
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField
import com.harshnandwani.digitaltijori.presentation.common_components.RoundedFilledButton
import com.harshnandwani.digitaltijori.presentation.common_components.RoundedOutlineButton
import com.harshnandwani.digitaltijori.presentation.company.CompaniesList
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.AddEditCredentialActivity
import com.harshnandwani.digitaltijori.presentation.util.CardHelperFunctions
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditCardScreen(viewModel: AddEditCardViewModel, onDone: () -> Unit) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val card = uiState.card.value
    val context = LocalContext.current
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var addCredentialClicked by remember { mutableStateOf(false) }

    BottomSheetLayout(
        sheetContent = {
            CompaniesList(
                titleText = "Select card issuer",
                companies = uiState.allCardIssuers,
                onSelect = {
                    viewModel.onEvent(CardEvent.SelectIssuer(it))
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        },
        sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FlipCardLayout(
                company = uiState.selectedIssuer,
                expiryNumber = uiState.expiryMonth + uiState.expiryYear,
                card = card,
                onIssuerLogoClick = {
                    if(uiState.mode == Parameters.VAL_MODE_ADD && !card.isLinkedToBank) {
                        focusManager.clearFocus()
                        coroutineScope.launch { bottomSheetState.show() }
                    }
                },
                backVisible = uiState.backVisible.value,
                onCardClick = { uiState.backVisible.value = !uiState.backVisible.value }
            )

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CardHelperFunctions.predefinedColorSchemes.forEach { colorScheme ->
                    Row(
                        Modifier
                            .clip(CircleShape)
                            .clickable { viewModel.onEvent(CardEvent.SelectedColorScheme(colorScheme)) }
                    ) {
                        Spacer(modifier = Modifier.size(16.dp).background(color = Color(colorScheme.bgColorFrom)).clip(CircleShape))
                        Spacer(modifier = Modifier.size(16.dp).background(color = Color(colorScheme.bgColorTo)).clip(CircleShape))
                    }
                }
            }

            InputTextField(
                label = "Card Number",
                value = card.cardNumber,
                onValueChange = { viewModel.onEvent(CardEvent.EnteredCardNumber(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = { number ->
                    CardHelperFunctions.formatCardNumber(card.cardNetwork, number)
                }
            )

            Row {
                InputTextField(
                    label = "Expiry",
                    value = uiState.expiryMonth + uiState.expiryYear,
                    onValueChange = { viewModel.onEvent(CardEvent.EnteredCardExpiry(it)) },
                    placeholder = "mm/yy",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = { CardHelperFunctions.formatExpiry(it) },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.weight(0.2f))

                InputTextField(
                    label = "CVV",
                    value = card.cvv,
                    onValueChange = { viewModel.onEvent(CardEvent.EnteredCvv(it)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            InputTextField(
                label = "Holder Name",
                value = card.nameOnCard,
                onValueChange = { viewModel.onEvent(CardEvent.EnteredNameOnCard(it)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            InputTextField(
                label = "Card Variant",
                value = card.variant ?: "",
                onValueChange = { viewModel.onEvent(CardEvent.EnteredVariant(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                placeholder = "Moneyback / ACE / Flipkart.."
            )

            Row {

                InputTextField(
                    label = "Pin",
                    value = card.pin,
                    onValueChange = { viewModel.onEvent(CardEvent.EnteredPin(it)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.weight(0.2f))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    var expanded by remember { mutableStateOf(false) }
                    val icon =
                        if (expanded) Icons.Filled.KeyboardArrowUp
                        else Icons.Filled.KeyboardArrowDown

                    InputTextField(
                        value = if (card.cardType == CardType.None) "" else card.cardType.name,
                        onValueChange = {  },
                        label = "Card Type",
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { expanded = !expanded })
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.onFocusChanged {
                            if (it.isFocused) expanded = true
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        cardsTypesList.forEach {
                            DropdownMenuItem(onClick = {
                                viewModel.onEvent(CardEvent.SelectedCardType(it))
                                expanded = false
                            }) {
                                Text(text = it.name)
                            }
                        }
                    }
                }

            }

            InputTextField(
                label = "Alias for card",
                value = card.cardAlias ?: "",
                onValueChange = { viewModel.onEvent(CardEvent.EnteredCardAlias(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.size(32.dp))

            if(uiState.selectedIssuer?.hasCredentials == true && uiState.mode == Parameters.VAL_MODE_ADD && card.isLinkedToBank){
                RoundedFilledButton(
                    onClick = {
                        addCredentialClicked = true
                        viewModel.onEvent(CardEvent.CardSubmit)
                    },
                    text = "Proceed to add credentials"
                )
                Spacer(modifier = Modifier.size(4.dp))
            }

            RoundedOutlineButton(
                onClick = { viewModel.onEvent(CardEvent.CardSubmit) },
                text = "Save card"
            )

            Spacer(modifier = Modifier.size(16.dp))

        }
    }

    LaunchedEffect(key1 = true) {
        if(uiState.mode == Parameters.VAL_MODE_ADD && !card.isLinkedToBank) {
            coroutineScope.launch { bottomSheetState.show() }
        }
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CardSubmitResultEvent.LinkCardWithIssuer -> {
                    addCredentialClicked = false
                    Toast.makeText(context, "Please select issuer", Toast.LENGTH_SHORT).show()
                    coroutineScope.launch { bottomSheetState.show() }
                }
                is CardSubmitResultEvent.ShowError -> {
                    addCredentialClicked = false
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                CardSubmitResultEvent.CardSaved -> {
                    Toast.makeText(context, "Card saved!", Toast.LENGTH_SHORT).show()
                    if (addCredentialClicked) {
                        Intent(context, AddEditCredentialActivity::class.java).apply {
                            putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_ADD)
                            putExtra(Parameters.KEY_IS_LINKED_TO_ACCOUNT, true)
                            putExtra(Parameters.KEY_ENTITY, uiState.selectedIssuer)
                            putExtra(Parameters.KEY_BANK_ACCOUNT, card.bankAccount)
                            context.startActivity(this)
                        }
                    }
                    onDone()
                }
            }
        }
    }

}
