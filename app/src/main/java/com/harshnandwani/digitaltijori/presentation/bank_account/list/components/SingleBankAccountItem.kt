package com.harshnandwani.digitaltijori.presentation.bank_account.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company

@Composable
fun SingleBankAccountItem(
    linkedBank: Company,
    account: BankAccount,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = linkedBank.iconResId),
            contentDescription = "Bank Icon",
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Text(
                text = if (account.alias.isNullOrEmpty()) account.holderName else account.alias,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "XXXXXXXX${account.accountNumber.takeLast(4)}",
                style = MaterialTheme.typography.body2
            )
        }
    }
}
