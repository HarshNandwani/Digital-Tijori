package com.harshnandwani.digitaltijori.presentation.card.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harshnandwani.digitaltijori.presentation.card.FlipCardLayout
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel

@ExperimentalMaterialApi
@Composable
fun CardsListScreen(viewModel: HomeViewModel) {

    val state = viewModel.state.value

    LazyColumn(Modifier.padding(16.dp)) {
        for ((issuer, card) in state.cards) {
            item {

                Column {
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
                        cardNetwork = card.cardNetwork
                    )

                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
        }
    }

}
