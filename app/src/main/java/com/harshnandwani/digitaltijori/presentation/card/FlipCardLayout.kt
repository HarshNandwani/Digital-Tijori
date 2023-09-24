package com.harshnandwani.digitaltijori.presentation.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.util.CardHelperFunctions

@Composable
fun FlipCardLayout(
    company: Company?,
    expiryNumber: String, //needed separately coz its type byte in entity
    card: Card,
    onIssuerLogoClick: () -> Unit = {},
    backVisible: Boolean,
    onCardClick: () -> Unit = {}
) {

    val textColor =
        if (card.colorScheme.textColor != 0)
            Color(card.colorScheme.textColor)
        else
            MaterialTheme.colors.onSurface

    val length = if (card.cardNumber.length > 16) 16 else card.cardNumber.length
    val initialCardNum = remember { "*****************" }
        .replaceRange(0..length, card.cardNumber.take(16))

    var pinVisible by remember { mutableStateOf(false) }
    val icon = if (pinVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility

    if (!backVisible)
        pinVisible = false

    FlipCard(
        backVisible = backVisible,
        onClick = onCardClick,
        front = {
            ConstraintLayout(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(card.colorScheme.bgColorFrom),
                                Color(card.colorScheme.bgColorTo)
                            )
                        )
                    )
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                val (cardVariant, issuerLogo, cardNumAndExpiryLayout, holderName, cardNetworkLogo) = createRefs()

                Text(
                    card.variant ?: "",
                    style = TextStyle(fontStyle = FontStyle.Italic),
                    modifier = Modifier
                        .constrainAs(cardVariant) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                    color = textColor
                )

                Image(
                    painter = painterResource(id = company?.logoResId ?: R.drawable.default_company_icon),
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
                        text = CardHelperFunctions.formatCardNumber(card.cardNetwork, AnnotatedString(initialCardNum)).text.replace("-".toRegex(), " "),
                        fontSize = 24.sp, //TODO: Remove hardcode
                        color = textColor
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Expires",
                            fontSize = 10.sp,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.size(2.dp))
                        Text(
                            text = expiryNumber.take(4).chunked(2).joinToString("/"),
                            color = textColor
                        )
                    }
                }

                val displayName: String = card.cardAlias.takeIf { !it.isNullOrEmpty() }
                    ?: if (card.nameOnCard.isEmpty()) "Card holder name" else card.nameOnCard

                Text(
                    text = displayName,
                    modifier = Modifier.constrainAs(holderName) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                    style = MaterialTheme.typography.body2,
                    color = textColor
                )

                Image(
                    painter = painterResource(
                        id = CardHelperFunctions.getDrawableIdForCardNetwork(
                            card.cardNetwork
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(card.colorScheme.bgColorFrom),
                                Color(card.colorScheme.bgColorTo)
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(top = 36.dp, bottom = 8.dp)
                        .height(48.dp)
                        .background(Color.Black)
                        .fillMaxWidth()
                )
                Text(
                    text = card.cvv.take(3),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Gray)
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "pin: ", color = textColor)
                    Text(
                        text = if (pinVisible) card.pin else "****",
                        style = MaterialTheme.typography.h6,
                        color = textColor
                    )
                    if (card.pin.isNotEmpty()) {
                        IconButton(onClick = { pinVisible = !pinVisible }) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Pin visibility icon"
                            )
                        }
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    )

}
