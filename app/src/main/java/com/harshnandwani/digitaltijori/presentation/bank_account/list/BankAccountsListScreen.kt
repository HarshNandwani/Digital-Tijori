package com.harshnandwani.digitaltijori.presentation.bank_account.list

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.AddEditBankAccountActivity
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.DetailedBankAccountActivity
import com.harshnandwani.digitaltijori.presentation.bank_account.list.components.SingleBankAccountItem
import com.harshnandwani.digitaltijori.presentation.common_components.Swipeable
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel
import com.harshnandwani.digitaltijori.presentation.util.BankAccountHelperFunctions
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun BankAccountsListScreen(viewModel: HomeViewModel) {

    val state = viewModel.state.value
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (state.bankAccounts.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalance,
                contentDescription = "",
                tint = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier.alpha(0.1f).fillMaxSize(0.5f)
            )
        }
        return
    }

    LazyColumn {
        state.filteredBankAccounts.forEach { bankAccount ->
            val linkedBank = bankAccount.linkedCompany
            item {
                Swipeable(
                    swipeToLeftEnabled = true,
                    rightColor = Color.Green,
                    rightIcon = Icons.Default.Share,
                    leftSwipeAction = {
                        /*
                        * The account is set in this lambda for one time,
                        * if user edits and then tries to share we will not
                        * be having the latest account details as it was set at first
                        *
                        * So, loading bank account again with the ID
                        * */
                        coroutineScope.launch {
                            val data = coroutineScope.async(Dispatchers.IO) {
                                viewModel.getBankAccount(bankAccount.bankAccountId)
                            }
                            withContext(Dispatchers.Main) {
                                val accountToShare = data.await()
                                if (accountToShare == null) {
                                    Toast.makeText(context, "Account is null", Toast.LENGTH_SHORT)
                                        .show()
                                    return@withContext
                                }
                                val shareIntent = BankAccountHelperFunctions.getShareIntent(linkedBank.name, accountToShare)
                                context.startActivity(shareIntent)
                            }
                        }
                    },
                    swipeToRightEnabled = true,
                    leftColor = Color.Magenta,
                    leftIcon = Icons.Default.Edit,
                    rightSwipeAction = {
                        /*
                       * The account is set in this lambda for one time,
                       * if user edits and then tries to edit again we will not
                       * be having the latest account details as it was set at first
                       *
                       * So, loading bank account again with the ID
                       * */
                        coroutineScope.launch {
                            val data = coroutineScope.async(Dispatchers.IO) {
                                viewModel.getBankAccount(bankAccount.bankAccountId)
                            }
                            withContext(Dispatchers.Main) {
                                val accountToEdit = data.await()
                                if (accountToEdit == null) {
                                    Toast.makeText(context, "Account is null", Toast.LENGTH_SHORT)
                                        .show()
                                    return@withContext
                                }
                                Intent(context, AddEditBankAccountActivity::class.java).apply {
                                    putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_EDIT)
                                    putExtra(Parameters.KEY_BANK, linkedBank)
                                    putExtra(Parameters.KEY_BANK_ACCOUNT, accountToEdit)
                                    context.startActivity(this)
                                }
                            }
                        }
                    }
                ) {
                    SingleBankAccountItem(
                        linkedBank = linkedBank,
                        account = bankAccount,
                        onClick = {
                            Intent(context, DetailedBankAccountActivity::class.java).apply {
                                putExtra(Parameters.KEY_BANK, linkedBank)
                                putExtra(Parameters.KEY_BANK_ACCOUNT, bankAccount)
                                context.startActivity(this)
                            }
                        }
                    )
                }
            }
        }
    }
}
