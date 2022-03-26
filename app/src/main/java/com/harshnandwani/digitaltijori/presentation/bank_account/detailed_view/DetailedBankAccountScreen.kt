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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.AddEditBankAccountActivity
import com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util.DetailedBankAccountEvent
import com.harshnandwani.digitaltijori.presentation.card.add_edit.AddEditCardActivity
import com.harshnandwani.digitaltijori.presentation.card.detailed_view.DetailedCard
import com.harshnandwani.digitaltijori.presentation.common_components.RoundedFilledButton
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.AddEditCredentialActivity
import com.harshnandwani.digitaltijori.presentation.credential.detailed_view.DetailedCredential
import com.harshnandwani.digitaltijori.presentation.util.Parameters

@ExperimentalMaterialApi
@Composable
fun DetailedBankAccountScreen(viewModel: DetailedBankAccountViewModel) {

    val state = viewModel.state.value
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = "Account details: ", style = MaterialTheme.typography.h1)
        Spacer(modifier = Modifier.size(8.dp))
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
                        painter = painterResource(id = state.bank.iconResId),
                        contentDescription = "Bank Icon",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = state.bank.name,
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
                                putExtra(Parameters.KEY_BANK, state.bank)
                                putExtra(Parameters.KEY_BANK_ACCOUNT, state.account)
                                ContextCompat.startActivity(context, this, null)
                            }
                            (context as DetailedBankAccountActivity).finish()
                        }
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Icon",
                        tint = Color.Red,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(DetailedBankAccountEvent.DeleteBankAccount)
                            (context as DetailedBankAccountActivity).finish()
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
                        Text(text = state.account.holderName)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = state.account.accountNumber)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = state.account.ifsc)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = state.account.phoneNumber ?: "-")
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(text = state.account.alias ?: "-")
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }
            }

        }
        Spacer(modifier = Modifier.size(24.dp))
        Text(text = "Linked Cards:", style = MaterialTheme.typography.h1)
        state.linkedCards.forEach { card ->
            Spacer(modifier = Modifier.size(16.dp))
            DetailedCard(
                titleText = "${card.cardType.name} Details",
                issuer = state.bank,
                card = card,
                onDeleteClick = {
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
                    putExtra(Parameters.KEY_ISSUER, state.bank)
                    putExtra(Parameters.KEY_BANK_ACCOUNT_ID, state.account.bankAccountId)
                    ContextCompat.startActivity(context, this, null)
                }
            },
            text = "add more cards"
        )

        Spacer(modifier = Modifier.size(24.dp))
        Text(text = "Linked Credentials:", style = MaterialTheme.typography.h1)
        state.linkedCredentials.forEach { credential ->
            Spacer(modifier = Modifier.size(16.dp))
            DetailedCredential(
                entity = state.bank,
                credential = credential,
                onDeleteClick = {
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
                    putExtra(Parameters.KEY_ENTITY, state.bank)
                    putExtra(Parameters.KEY_BANK_ACCOUNT_ID, state.account.bankAccountId)
                    ContextCompat.startActivity(context, this, null)
                }
            },
            text = "add more credentials"
        )
    }

}
