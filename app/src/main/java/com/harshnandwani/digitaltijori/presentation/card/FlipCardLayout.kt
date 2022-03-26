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
    pin: String,
    cardNetwork: CardNetwork,
    onIssuerLogoClick: () -> Unit = {},
    backVisible: Boolean,
    onCardClick: () -> Unit = {}
) {

    val length = if (cardNumber.length > 16) 16 else cardNumber.length
    val initialCardNum = remember { "*****************" }
        .replaceRange(0..length, cardNumber.take(16))

    var pinVisible by remember { mutableStateOf(false) }
    val icon = if (pinVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility

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
                        initialCardNum.chunked(4).joinToString(" "),
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
                    text = if(nameText.isEmpty()) "Card holder name" else nameText,
                    modifier = Modifier.constrainAs(holderName) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                    style = MaterialTheme.typography.body2
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(
                    modifier = Modifier
                        .padding(top = 36.dp, bottom = 8.dp)
                        .height(48.dp)
                        .background(Color.Black)
                        .fillMaxWidth()
                )
                Text(
                    text = cvvNumber.take(3),
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
                    Text(text = "pin: ")
                    Text(
                        text = if (pinVisible) pin else "****",
                        style = MaterialTheme.typography.h6
                    )
                    if (pin.isNotEmpty()) {
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
