package com.harshnandwani.digitaltijori.presentation.card.detailed_view

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.card.FlipCardLayout
import com.harshnandwani.digitaltijori.presentation.card.add_edit.AddEditCardActivity
import com.harshnandwani.digitaltijori.presentation.common_components.ConfirmationAlertDialog
import com.harshnandwani.digitaltijori.presentation.util.CardHelperFunctions
import com.harshnandwani.digitaltijori.presentation.util.Parameters

@ExperimentalMaterialApi
@Composable
fun DetailedCard(
    titleText: String,
    issuer: Company,
    card: Card,
    onDeleteAction: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    var backVisible by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(
                text = titleText,
                Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Icon",
                modifier = Modifier.clickable {
                    Intent(context, AddEditCardActivity::class.java).apply {
                        putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_EDIT)
                        putExtra(Parameters.KEY_ISSUER, issuer)
                        putExtra(Parameters.KEY_CARD, card)
                        ContextCompat.startActivity(context, this, null)
                    }
                    if (context is DetailedCardActivity)
                        context.finish()
                }
            )
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Icon",
                modifier = Modifier.clickable {
                    showDialog = true
                }
            )
        }

        ConfirmationAlertDialog(
            visible = showDialog,
            onDismiss = { showDialog = false },
            title = "You are deleting ${issuer.name} ${card.cardType.name}",
            text = "This action cannot be undone, do you want to proceed?",
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteAction()
                        showDialog = false
                    }
                ) {
                    Text(text = "Yes, delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )

        FlipCardLayout(
            company = issuer,
            expiryNumber = CardHelperFunctions.getMonthAsString(card.expiryMonth) + card.expiryYear.toString(),
            card = card,
            backVisible = backVisible,
            onCardClick = { backVisible = !backVisible }
        )
    }

}