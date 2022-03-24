package com.harshnandwani.digitaltijori.presentation.card.list

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.harshnandwani.digitaltijori.presentation.bank_account.list.components.Swipeable
import com.harshnandwani.digitaltijori.presentation.card.FlipCardLayout
import com.harshnandwani.digitaltijori.presentation.card.add_edit.AddEditCardActivity
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalMaterialApi
@Composable
fun CardsListScreen(viewModel: HomeViewModel) {

    val state = viewModel.state.value
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(Modifier.padding(16.dp)) {
        for ((issuer, card) in state.filteredCards) {
            item {

                Column {

                    Swipeable(
                        swipeToLeftEnabled = true,
                        swipeToRightEnabled = true,
                        leftColor = Color.Magenta,
                        leftIcon = Icons.Default.Edit,
                        rightSwipeAction = {
                            coroutineScope.launch {
                                val data = coroutineScope.async(Dispatchers.IO) {
                                    viewModel.getCard(card.cardId)
                                }
                                withContext(Dispatchers.Main) {
                                    val cardToEdit = data.await()
                                    if (cardToEdit == null) {
                                        Toast.makeText(context, "Card is null", Toast.LENGTH_SHORT)
                                            .show()
                                        return@withContext
                                    }
                                    Intent(context, AddEditCardActivity::class.java).apply {
                                        putExtra(Parameters.KEY_MODE, Parameters.VAL_MODE_EDIT)
                                        putExtra(Parameters.KEY_ISSUER, issuer)
                                        putExtra(Parameters.KEY_CARD, card)
                                        ContextCompat.startActivity(context, this, null)
                                    }
                                }
                            }
                        }
                    ) {
                        val cardNumberDisplay =
                            "x".repeat(card.cardNumber.length - 4) +
                                    card.cardNumber.takeLast(4)

                        FlipCardLayout(
                            variant = "",
                            company = issuer,
                            nameText = card.nameOnCard,
                            cardNumber = cardNumberDisplay,
                            expiryNumber = "xxxx",
                            cvvNumber = "",
                            cardNetwork = card.cardNetwork,
                            backVisible = false
                        )
                    }

                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
        }
    }

}
