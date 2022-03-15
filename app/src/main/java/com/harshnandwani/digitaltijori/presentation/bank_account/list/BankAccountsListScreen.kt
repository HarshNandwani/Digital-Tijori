package com.harshnandwani.digitaltijori.presentation.bank_account.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.harshnandwani.digitaltijori.presentation.bank_account.list.components.SingleBankAccountItem
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel

@Composable
fun BankAccountsListScreen(viewModel: HomeViewModel) {

    val state = viewModel.state.value

    LazyColumn {
        for ((linkedBank, bankAccount) in state.filteredBankAccounts) {
            item {
                SingleBankAccountItem(
                    linkedBank = linkedBank,
                    account = bankAccount,
                    onClick = {
                        //TODO: Show BankAccount complete details
                    }
                )
            }
        }
    }
}
