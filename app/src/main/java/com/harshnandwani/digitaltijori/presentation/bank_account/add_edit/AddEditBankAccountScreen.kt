package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util.BankAccountEvent
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util.BankAccountSubmitResultEvent
import com.harshnandwani.digitaltijori.presentation.card.add_edit.AddEditCardActivity
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField
import com.harshnandwani.digitaltijori.presentation.common_components.RoundedFilledButton
import com.harshnandwani.digitaltijori.presentation.common_components.RoundedOutlineButton
import com.harshnandwani.digitaltijori.presentation.company.CompaniesList
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditBankAccountScreen(viewModel: AddEditBankAccountViewModel, onDone: () -> Unit) {

    val context = LocalContext.current
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var addCardsClicked by remember { mutableStateOf(false) }

    ModalBottomSheetLayout(
        sheetContent = {
            CompaniesList(
                titleText = "Select a bank",
                companies = uiState.allBanks,
                onSelect = {
                    viewModel.onEvent(BankAccountEvent.SelectBank(it))
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
                .padding(horizontal = 36.dp, vertical = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    if (uiState.mode == Parameters.VAL_MODE_ADD) {
                        focusManager.clearFocus()
                        coroutineScope.launch { bottomSheetState.show() }
                    }
                }
            ) {
                Image(
                    painter = painterResource(id = uiState.selectedBank?.iconResId ?: R.drawable.default_company_icon),
                    contentDescription = "Bank Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = uiState.selectedBank?.name ?: "Select bank")
            }
            Spacer(modifier = Modifier.size(16.dp))
            val account = uiState.bankAccount.value
            InputTextField(
                label = "Account Number",
                value = account.accountNumber,
                onValueChange = {
                    viewModel.onEvent(BankAccountEvent.EnteredAccountNumber(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            InputTextField(
                label = "IFSC",
                value = account.ifsc,
                onValueChange = {
                    viewModel.onEvent(BankAccountEvent.EnteredIFSC(it))
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            InputTextField(
                label = "Account Holder Name",
                value = account.holderName,
                onValueChange = {
                    viewModel.onEvent(BankAccountEvent.EnteredHolderName(it))
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            InputTextField(
                label = "Linked phone number (optional)",
                value = account.phoneNumber ?: "",
                onValueChange = {
                    viewModel.onEvent(BankAccountEvent.EnteredPhoneNumber(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            InputTextField(
                label = "Alias for this account (optional)",
                value = account.alias ?: "",
                onValueChange = {
                    viewModel.onEvent(BankAccountEvent.EnteredAlias(it))
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.size(32.dp))

            if(uiState.selectedBank?.issuesCards == true && uiState.mode == Parameters.VAL_MODE_ADD) {
                RoundedFilledButton(
                    onClick = {
                        focusManager.clearFocus()
                        addCardsClicked = true
                        viewModel.onEvent(BankAccountEvent.BankAccountSubmit)
                    },
                    text = "Proceed to add card"
                )
                Spacer(modifier = Modifier.size(4.dp))
            }

            RoundedOutlineButton(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.onEvent(BankAccountEvent.BankAccountSubmit)
                },
                text = "Save account"
            )

        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BankAccountSubmitResultEvent.BankAccountSaved -> {
                    Toast.makeText(context, "Bank Account saved!", Toast.LENGTH_SHORT).show()
                    if(addCardsClicked){
                        Intent(context, AddEditCardActivity::class.java).apply {
                            putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_ADD)
                            putExtra(Parameters.KEY_IS_LINKED_TO_ACCOUNT, true)
                            putExtra(Parameters.KEY_ISSUER, event.linkedBank)
                            putExtra(Parameters.KEY_BANK_ACCOUNT, uiState.bankAccount.value)
                            context.startActivity(this)
                        }
                    }
                    onDone()
                }
                is BankAccountSubmitResultEvent.ShowError -> {
                    addCardsClicked = false
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
