package com.harshnandwani.digitaltijori.presentation.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.presentation.util.CardHelperFunctions

@ExperimentalMaterialApi
@Composable
fun FlipCardLayout(
    variant: String,
    company: Company?,
    nameText: String,
    cardNumber: String,
    expiryNumber: String,
    cvvNumber: String,
    cardNetwork: CardNetwork,
    onIssuerLogoClick: () -> Unit = {},
    backVisible: Boolean,
    onCardClick: () -> Unit = {}
) {

    val initial = remember { "*****************" }
        .replaceRange(0..16, cardNumber.take(16))

    FlipCard(
        backVisible = backVisible,
        onClick = onCardClick,
        front = {
            ConstraintLayout(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
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
                    painter = painterResource(id = company?.logoResId ?: R.drawable.default_bank),
                    contentDescription = "Issuer Logo",
                    modifier = Modifier
                        .constrainAs(issuerLogo) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            onIssuerLogoClick()
                        }
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
                        Spacer(modifier = Modifier.size(2.dp))
                        Text(
                            text = expiryNumber.take(4).chunked(2).joinToString("/"),
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
                    painter = painterResource(
                        id = CardHelperFunctions.getDrawableIdForCardNetwork(
                            cardNetwork
                        )
                    ),
                    contentDescription = "Card Network",
                    modifier = Modifier.constrainAs(cardNetworkLogo) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                )

            }
        },
        back = {
            Column {
                Spacer(
                    modifier = Modifier
                        .padding(top = 36.dp)
                        .height(48.dp)
                        .background(Color.Black)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(24.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .align(Alignment.CenterHorizontally)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cvvNumber.take(3),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    )

}
