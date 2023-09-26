package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.AddEditBankAccountActivity
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountEvent
import com.harshnandwani.digitaltijori.presentation.card.add_edit.AddEditCardActivity
import com.harshnandwani.digitaltijori.presentation.card.detailed_view.DetailedCard
import com.harshnandwani.digitaltijori.presentation.common_components.ConfirmationAlertDialog
import com.harshnandwani.digitaltijori.presentation.common_components.RoundedFilledButton
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.AddEditCredentialActivity
import com.harshnandwani.digitaltijori.presentation.credential.detailed_view.DetailedCredential
import com.harshnandwani.digitaltijori.presentation.util.Parameters

@Composable
fun DetailedBankAccountScreen(viewModel: DetailedBankAccountViewModel) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showAccountDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = "Account: ", style = MaterialTheme.typography.h1)
        Spacer(modifier = Modifier.size(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 16.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(Modifier.padding(24.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = uiState.bank.iconResId),
                        contentDescription = "Bank Icon",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = uiState.bank.name,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.weight(weight = 1f)
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Icon",
                        modifier = Modifier.clickable {
                            Intent(context, AddEditBankAccountActivity::class.java).apply {
                                putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_EDIT)
                                putExtra(Parameters.KEY_BANK, uiState.bank)
                                putExtra(Parameters.KEY_BANK_ACCOUNT, uiState.account)
                                context.startActivity(this)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Icon",
                        modifier = Modifier.clickable {
                            showAccountDialog = true
                        }
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))
                Row {
                    Column {
                        Text(text = "Account Holder", style = MaterialTheme.typography.h2)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = "Account Number", style = MaterialTheme.typography.h2)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = "IFSC", style = MaterialTheme.typography.h2)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = "Phone Number", style = MaterialTheme.typography.h2)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = "Alias", style = MaterialTheme.typography.h2)
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                    Spacer(modifier = Modifier.size(32.dp))
                    Column {
                        Text(text = uiState.account.holderName)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = uiState.account.accountNumber)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = uiState.account.ifsc)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = uiState.account.phoneNumber ?: "-")
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = uiState.account.alias ?: "-")
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }

                ConfirmationAlertDialog(
                    visible = showAccountDialog,
                    onDismiss = { showAccountDialog = false },
                    title = "Do you want to delete ${uiState.bank.name} account",
                    text = "This action cannot be undone, do you want to proceed?",
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.onEvent(DetailedBankAccountEvent.DeleteBankAccount)
                                showAccountDialog = false
                            }
                        ) {
                            Text(text = "Yes, delete")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAccountDialog = false }) {
                            Text(text = "Cancel")
                        }
                    }
                )

            }

        }

        // Not the best way to show dotted line/border :P But am yet to learn Canvas, so here we go
        Text(
            text = "- ".repeat(100),
            maxLines = 1,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        if (uiState.bank.issuesCards) {
            Text(text = "Linked Cards:", style = MaterialTheme.typography.h1)
            uiState.linkedCards.forEach { card ->
                Spacer(modifier = Modifier.size(16.dp))
                DetailedCard(
                    titleText = "${card.cardType.name} Details",
                    issuer = uiState.bank,
                    card = card,
                    onDeleteAction = {
                        viewModel.onEvent(DetailedBankAccountEvent.DeleteCard(card))
                    }
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            RoundedFilledButton(
                onClick = {
                    Intent(context, AddEditCardActivity::class.java).apply {
                        putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_ADD)
                        putExtra(Parameters.KEY_IS_LINKED_TO_ACCOUNT, true)
                        putExtra(Parameters.KEY_ISSUER, uiState.bank)
                        putExtra(Parameters.KEY_BANK_ACCOUNT, uiState.account)
                        context.startActivity(this)
                    }
                },
                text = "add more cards",
                cornerSize = 16.dp
            )
        }

        // Oh Again? Sorry, its the last time!
        Text(
            text = "- ".repeat(100),
            maxLines = 1,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        if (uiState.bank.hasCredentials) {
            Text(text = "Linked Credentials:", style = MaterialTheme.typography.h1)
            uiState.linkedCredentials.forEach { credential ->
                Spacer(modifier = Modifier.size(16.dp))
                DetailedCredential(
                    entity = uiState.bank,
                    credential = credential,
                    onDeleteAction = {
                        viewModel.onEvent(DetailedBankAccountEvent.DeleteCredential(credential))
                    }
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            RoundedFilledButton(
                onClick = {
                    Intent(context, AddEditCredentialActivity::class.java).apply {
                        putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_ADD)
                        putExtra(Parameters.KEY_IS_LINKED_TO_ACCOUNT, true)
                        putExtra(Parameters.KEY_ENTITY, uiState.bank)
                        putExtra(Parameters.KEY_BANK_ACCOUNT, uiState.account)
                        context.startActivity(this)
                    }
                },
                text = "add more credentials",
                cornerSize = 16.dp
            )
        }

    }

}
