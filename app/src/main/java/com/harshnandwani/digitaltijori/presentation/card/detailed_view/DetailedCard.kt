package com.harshnandwani.digitaltijori.presentation.card.detailed_view

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.card.FlipCardLayout
import com.harshnandwani.digitaltijori.presentation.card.add_edit.AddEditCardActivity
import com.harshnandwani.digitaltijori.presentation.common_components.DeleteConfirmationDialog
import com.harshnandwani.digitaltijori.presentation.util.CardHelperFunctions
import com.harshnandwani.digitaltijori.presentation.util.Parameters

@Composable
fun DetailedCard(
    titleText: String,
    issuer: Company,
    card: Card,
    onDeleteAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDone: () -> Unit = {}
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
                        context.startActivity(this)
                    }
                    onDone()
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

        DeleteConfirmationDialog(
            visible = showDialog,
            onDismiss = { showDialog = false },
            title = "You are deleting ${issuer.name} ${card.cardType.name}",
            text = "This action cannot be undone, do you want to proceed?",
            onDelete = {
                onDeleteAction()
                showDialog = false
            },
            onCancel = { showDialog = false }
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