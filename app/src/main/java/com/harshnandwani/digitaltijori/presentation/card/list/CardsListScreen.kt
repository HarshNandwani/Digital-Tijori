package com.harshnandwani.digitaltijori.presentation.card.list

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harshnandwani.digitaltijori.presentation.common_components.Swipeable
import com.harshnandwani.digitaltijori.presentation.card.FlipCardLayout
import com.harshnandwani.digitaltijori.presentation.card.add_edit.AddEditCardActivity
import com.harshnandwani.digitaltijori.presentation.card.detailed_view.DetailedCardActivity
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CardsListScreen(viewModel: HomeViewModel) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (uiState.cards.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.CreditCard,
                contentDescription = "",
                modifier = Modifier.alpha(0.1f).fillMaxSize(0.5f)
            )
        }
        return
    }

    LazyColumn {
        uiState.filteredCards.forEach { card ->
            item {
                Column(Modifier.padding(16.dp)) {
                    Box(Modifier.clip(RoundedCornerShape(24.dp))) {
                        Swipeable(
                            swipeToLeftEnabled = true,
                            rightIcon = Icons.Default.ContentCopy,
                            leftSwipeAction = {
                                coroutineScope.launch {
                                    val data = coroutineScope.async(Dispatchers.IO) {
                                        viewModel.getCard(card.cardId)
                                    }
                                    withContext(Dispatchers.Main) {
                                        val cardToCopy = data.await()
                                        if (cardToCopy == null) {
                                            Toast.makeText(context, "Card is null", Toast.LENGTH_SHORT).show()
                                            return@withContext
                                        }
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip: ClipData = ClipData.newPlainText("Card Number", cardToCopy.cardNumber)
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "Card number Copied", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            swipeToRightEnabled = true,
                            leftIcon = Icons.Default.Edit,
                            rightSwipeAction = {
                                coroutineScope.launch {
                                    val data = coroutineScope.async(Dispatchers.IO) {
                                        viewModel.getCard(card.cardId)
                                    }
                                    withContext(Dispatchers.Main) {
                                        val cardToEdit = data.await()
                                        if (cardToEdit == null) {
                                            Toast.makeText(context, "Card is null", Toast.LENGTH_SHORT).show()
                                            return@withContext
                                        }
                                        Intent(context, AddEditCardActivity::class.java).apply {
                                            putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_EDIT)
                                            putExtra(Parameters.KEY_ISSUER, card.company)
                                            putExtra(Parameters.KEY_CARD, cardToEdit)
                                            context.startActivity(this)
                                        }
                                    }
                                }
                            }
                        ) {
                            val cardNumberDisplay =
                                "x".repeat(card.cardNumber.length - 4) +
                                        card.cardNumber.takeLast(4)

                            val cardToDisplay = card.copy(
                                cardNumber = cardNumberDisplay,
                                expiryMonth = -1,
                                expiryYear = -1,
                                cvv = "",
                                pin = ""
                            )

                            FlipCardLayout(
                                company = card.company,
                                expiryNumber = "xxxx",
                                card = cardToDisplay,
                                backVisible = false,
                                onCardClick = {
                                    Intent(context, DetailedCardActivity::class.java).apply {
                                        putExtra(Parameters.KEY_ISSUER, card.company)
                                        putExtra(Parameters.KEY_CARD, card)
                                        context.startActivity(this)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}
