package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit

import android.app.Activity
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
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

@ExperimentalComposeUiApi
@ExperimentalMaterialApi //TODO: As its Experimental keep looking into changes
@Composable
fun AddEditBankAccountScreen(viewModel: AddEditBankAccountViewModel) {

    val activity = LocalContext.current as Activity
    val state = viewModel.state.value

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    var addCardsClicked by remember { mutableStateOf(false) }
//    var addCredentialsClicked by remember { mutableStateOf(false) }

    ModalBottomSheetLayout(
        sheetContent = {
            CompaniesList(
                titleText = "Select a bank",
                companies = state.allBanks,
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
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    if (state.mode == Parameters.VAL_MODE_ADD) {
                        keyboardController?.hide()
                        coroutineScope.launch { bottomSheetState.show() }
                    }
                }
            ) {
                Image(
                    painter = painterResource(id = state.selectedBank?.iconResId ?: R.drawable.default_company_icon),
                    contentDescription = "Bank Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = state.selectedBank?.name ?: "Select bank")
            }
            val account = state.bankAccount.value
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

//            if (state.selectedBank?.hasCredentials == true && state.mode == Parameters.VAL_MODE_ADD) {
//                Button(
//                    onClick = {
//                        viewModel.onEvent(BankAccountEvent.BankAccountSubmit)
//                        addCredentialsClicked = true
//                    }
//                ) {
//                    Text(text = "Add Credentials for this account")
//                }
//            }

            Spacer(modifier = Modifier.size(16.dp))

            if(state.selectedBank?.issuesCards == true && state.mode == Parameters.VAL_MODE_ADD) {
                RoundedFilledButton(
                    onClick = {
                        addCardsClicked = true
                        viewModel.onEvent(BankAccountEvent.BankAccountSubmit)
                    },
                    text = "Proceed to add card"
                )
            }

            RoundedOutlineButton(
                onClick = { viewModel.onEvent(BankAccountEvent.BankAccountSubmit) },
                text = "Save account"
            )

        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BankAccountSubmitResultEvent.BankAccountSaved -> {
                    Toast.makeText(activity, "Bank Account saved!", Toast.LENGTH_SHORT).show()
                    if(addCardsClicked){
                        Intent(activity, AddEditCardActivity::class.java).apply {
                            putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_ADD)
                            putExtra(Parameters.KEY_IS_LINKED_TO_ACCOUNT, true)
                            putExtra(Parameters.KEY_ISSUER, event.linkedBank)
                            putExtra(Parameters.KEY_BANK_ACCOUNT_ID, event.accountId)
                            startActivity(activity, this, null)
                        }
                    }
//                    if (addCredentialsClicked) {
//                        Intent(activity, AddEditCredentialActivity::class.java).apply {
//                            putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_ADD)
//                            putExtra(Parameters.KEY_ENTITY, event.linkedBank)
//                            putExtra(Parameters.KEY_BANK_ACCOUNT_ID, event.accountId)
//                            startActivity(activity, this, null)
//                        }
//                    }
                    activity.finish()
                }
                is BankAccountSubmitResultEvent.ShowError -> {
                    Toast.makeText(activity, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
