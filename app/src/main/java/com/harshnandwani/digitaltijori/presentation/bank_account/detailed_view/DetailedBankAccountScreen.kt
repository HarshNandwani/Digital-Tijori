package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.harshnandwani.digitaltijori.presentation.util.Parameters

@Composable
fun DetailedBankAccountScreen(viewModel: DetailedBankAccountViewModel) {

    val state = viewModel.state.value
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(16.dp)
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
                Text(text = state.bank.name, style = MaterialTheme.typography.h1)
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
}
