package com.harshnandwani.digitaltijori.presentation.credential.list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

@Composable
fun SingleCredentialItem(
    linkedEntity: Company,
    credential: Credential,
    onClick: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = linkedEntity.iconResId),
            contentDescription = "Entity Icon",
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            if (!credential.alias.isNullOrBlank()) {
                Text(
                    text = credential.alias,
                    style = MaterialTheme.typography.body2
                )
            }
            Text(
                text = credential.username,
                style = MaterialTheme.typography.body2
            )
        }
    }

}
