package com.harshnandwani.digitaltijori.presentation.bank_account.list

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.harshnandwani.digitaltijori.presentation.bank_account.list.components.SingleBankAccountItem
import com.harshnandwani.digitaltijori.presentation.bank_account.list.components.Swipeable
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel
import com.harshnandwani.digitaltijori.presentation.util.BankAccountHelperFunction

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun BankAccountsListScreen(viewModel: HomeViewModel) {

    val state = viewModel.state.value
    val context = LocalContext.current

    LazyColumn {
        for ((linkedBank, bankAccount) in state.filteredBankAccounts) {
            item {
                Swipeable(
                    swipeToLeftEnabled = true,
                    rightColor = Color.Green,
                    rightIcon = Icons.Default.Share,
                    leftSwipeAction = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, BankAccountHelperFunction.getFormattedBankAccount(linkedBank.name, bankAccount))
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        ContextCompat.startActivity(context, shareIntent, null)
                    },
                    swipeToRightEnabled = true,
                    leftColor = Color.Magenta,
                    leftIcon = Icons.Default.Edit
                ) {
                    SingleBankAccountItem(
                        linkedBank = linkedBank,
                        account = bankAccount,
                        onClick = {
                            //TODO: Show BankAccount complete details
                        },
                        onLongClick = {
                            //TODO: Delete Account
                        }
                    )
                }
            }
        }
    }
}
