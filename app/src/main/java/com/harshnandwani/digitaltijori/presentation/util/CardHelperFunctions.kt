package com.harshnandwani.digitaltijori.presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.ColorScheme

class CardHelperFunctions {

    companion object {
        fun getDrawableIdForCardNetwork(cardNetwork: CardNetwork): Int {
            return when (cardNetwork) {
                CardNetwork.Visa -> R.drawable.visa_logo
                CardNetwork.MasterCard -> R.drawable.mastercard_logo
                CardNetwork.Rupay -> R.drawable.rupay_logo
                CardNetwork.AmEx -> R.drawable.american_express_logo
                CardNetwork.DinersClub -> R.drawable.diners_club_logo
                CardNetwork.Unknown -> R.drawable.transparent
            }
        }

        fun formatExpiry(expiry: AnnotatedString): TransformedText {
            var out = ""
            for (i in expiry.indices) {
                out += expiry[i]
                if (i == 1) out += "/"
            }

            val expiryOffsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return if (offset <= 1) {
                        offset
                    } else {
                        offset + 1
                    }
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return if (offset <= 1) {
                        offset
                    } else {
                        offset - 1
                    }
                }
            }

            return TransformedText(AnnotatedString(out), expiryOffsetTranslator)
        }

        fun getMonthAsString(month: Byte):String {
            return if (month < 10) "0$month" else month.toString()
        }

        /*
        * Credits to Benyam
        * https://dev.to/benyam7/formatting-credit-card-number-input-in-jetpack-compose-android-2nal
        * */
        fun formatCardNumber(
            cardNetwork: CardNetwork,
            cardNumber: AnnotatedString
        ): TransformedText {
            return when (cardNetwork) {
                CardNetwork.AmEx -> formatAmex(cardNumber)
                CardNetwork.DinersClub -> formatDinnersClub(cardNumber)
                else -> formatOtherCardNumbers(cardNumber)
            }
        }

        private fun formatAmex(text: AnnotatedString): TransformedText {
            val trimmed = if (text.text.length >= 15) text.text.substring(0..14) else text.text
            var out = ""

            for (i in trimmed.indices) {
                out += trimmed[i]
                //put - character at 3rd and 9th indicies
                if (i == 3 || i == 9 && i != 14) out += "-"
            }
            //    xxxx-xxxxxx-xxxxx
            /**
             * The offset translator should ignore the hyphen characters, so conversion from
             *  original offset to transformed text works like
             *  - The 4th char of the original text is 5th char in the transformed text. (i.e original[4th] == transformed[5th]])
             *  - The 11th char of the original text is 13th char in the transformed text. (i.e original[11th] == transformed[13th])
             *  Similarly, the reverse conversion works like
             *  - The 5th char of the transformed text is 4th char in the original text. (i.e  transformed[5th] == original[4th] )
             *  - The 13th char of the transformed text is 11th char in the original text. (i.e transformed[13th] == original[11th])
             */
            val creditCardOffsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 3) return offset
                    if (offset <= 9) return offset + 1
                    if (offset <= 15) return offset + 2
                    return 17
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 4) return offset
                    if (offset <= 11) return offset - 1
                    if (offset <= 17) return offset - 2
                    return 15
                }
            }
            return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
        }

        private fun formatDinnersClub(text: AnnotatedString): TransformedText {
            val trimmed = if (text.text.length >= 14) text.text.substring(0..13) else text.text
            var out = ""

            for (i in trimmed.indices) {
                out += trimmed[i]
                if (i == 3 || i == 9 && i != 13) out += "-"
            }

            //xxxx-xxxxxx-xxxx
            val creditCardOffsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 3) return offset
                    if (offset <= 9) return offset + 1
                    if (offset <= 14) return offset + 2
                    return 16
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 4) return offset
                    if (offset <= 11) return offset - 1
                    if (offset <= 16) return offset - 2
                    return 14
                }
            }
            return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
        }

        private fun formatOtherCardNumbers(text: AnnotatedString): TransformedText {

            val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
            var out = ""

            for (i in trimmed.indices) {
                out += trimmed[i]
                if (i % 4 == 3 && i != 15) out += "-"
            }
            val creditCardOffsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 3) return offset
                    if (offset <= 7) return offset + 1
                    if (offset <= 11) return offset + 2
                    if (offset <= 16) return offset + 3
                    return 19
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 4) return offset
                    if (offset <= 9) return offset - 1
                    if (offset <= 14) return offset - 2
                    if (offset <= 19) return offset - 3
                    return 16
                }
            }

            return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
        }

        val predefinedColorSchemes = listOf(
            ColorScheme(Color(0xFF5FA2D7).toArgb(), Color(0xFF9CBDE4).toArgb()),
            ColorScheme(Color(0xFF1E477A).toArgb(), Color(0xFF1C72C2).toArgb(), Color.White.toArgb()),
            ColorScheme(Color(0xFF161616).toArgb(), Color(0xFF585858).toArgb(), Color.White.toArgb()),
        )

    }

}
