package com.harshnandwani.digitaltijori.presentation.card.list

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

                var expiryNumberDisplay =
                    if (card.expiryMonth < 10)
                        "0${card.expiryMonth}"
                    else
                        card.expiryMonth.toString()

                expiryNumberDisplay += card.expiryYear.toString()

                FlipCardLayout(
                    variant = "",
                    company = issuer,
                    nameText = card.nameOnCard,
                    cardNumber = card.cardNumber,
                    expiryNumber = expiryNumberDisplay,
                    cvvNumber = card.cvv,
                    cardNetwork = card.cardNetwork
                )
                Spacer(modifier = Modifier.size(24.dp))
            }
        }
    }

}
