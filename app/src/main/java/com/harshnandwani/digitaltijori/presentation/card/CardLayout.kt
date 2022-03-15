package com.harshnandwani.digitaltijori.presentation.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.presentation.util.getDrawableIdForCardNetwork

@Composable
fun CardLayout(
    variant: String,
    company: Company,
    nameText: String,
    cardNumber: String,
    expiryNumber: String,
    cvvNumber: String,
    cardNetwork: CardNetwork
) {

    val initial = remember { "*****************" }
        .replaceRange(0..16, cardNumber.take(16))

    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(220.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(16.dp)
        ) {
            val (cardVariant, issuerLogo, cardNumAndExpiryLayout, holderName, cardNetworkLogo) = createRefs()

            Text(
                variant,
                style = TextStyle(fontStyle = FontStyle.Italic),
                modifier = Modifier
                    .constrainAs(cardVariant) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )

            Image(
                painter = painterResource(id = company.logoResId),
                contentDescription = "Issuer Logo",
                modifier = Modifier
                    .constrainAs(issuerLogo) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
            )

            Column(
                modifier = Modifier
                    .constrainAs(cardNumAndExpiryLayout) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(issuerLogo.bottom)
                        bottom.linkTo(cardNetworkLogo.top)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    initial.chunked(4).joinToString(" "),
                    fontSize = 24.sp, //TODO: Remove hardcode
                )

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Expires",
                        fontSize = 10.sp,
                    )

                    Text(
                        text = expiryNumber.take(4).chunked(2).joinToString(" / "),
                    )
                }
            }

            Text(
                text = nameText,
                modifier = Modifier.constrainAs(holderName) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
            )

            Image(
                painter = painterResource(id = getDrawableIdForCardNetwork(cardNetwork)),
                contentDescription = "Card Network",
                modifier = Modifier.constrainAs(cardNetworkLogo) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )

        }
    }
}
