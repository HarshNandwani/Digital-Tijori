package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util.BankAccountEvent
import com.harshnandwani.digitaltijori.presentation.common_components.InputTextField
import com.harshnandwani.digitaltijori.presentation.common_components.TopAppBarWithBackButton

@Composable
fun AddEditBankAccountScreen(viewModel: AddEditBankAccountViewModel) {

    val state = viewModel.state.value

    Scaffold(topBar = { TopAppBarWithBackButton(title = "Provide account details") }) {
        Column(Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.default_bank), //TODO: Show bank logo
                    contentDescription = "Bank Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Bank Name") // TODO: Show bank name
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
                value = account.phoneNumber?: "",
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
                value = account.alias?: "",
                onValueChange = {
                    viewModel.onEvent(BankAccountEvent.EnteredAlias(it))
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            TextButton(
                onClick = {
                    viewModel.onEvent(BankAccountEvent.BankAccountSubmit)
                },
                content = {
                    Text(text = "Submit")
                }
            )
        }
    }
}
