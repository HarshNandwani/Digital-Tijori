package com.harshnandwani.digitaltijori.presentation.card.detailed_view

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import com.harshnandwani.digitaltijori.presentation.util.CardHelperFunctions
import com.harshnandwani.digitaltijori.presentation.util.Parameters

@ExperimentalMaterialApi
@Composable
fun DetailedCard(
    titleText: String,
    issuer: Company,
    card: Card,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    var backVisible by remember { mutableStateOf(false) }

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
                    (context as Activity).finish()
                }
            )
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Icon",
                tint = Color.Red,
                modifier = Modifier.clickable {
                    onDeleteClick()
                }
            )
        }
        FlipCardLayout(
            variant = card.variant ?: "",
            company = issuer,
            nameText = card.nameOnCard,
            cardNumber = card.cardNumber,
            expiryNumber = CardHelperFunctions.getMonthAsString(card.expiryMonth) + card.expiryYear.toString(),
            cvvNumber = card.cvv,
            pin = card.pin,
            cardNetwork = card.cardNetwork,
            backVisible = backVisible,
            onCardClick = { backVisible = !backVisible }
        )
    }

}