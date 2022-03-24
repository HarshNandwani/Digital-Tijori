package com.harshnandwani.digitaltijori.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.CardType
import com.harshnandwani.digitaltijori.domain.util.InvalidCardException
import java.io.Serializable
import kotlin.jvm.Throws

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = BankAccount::class,
            parentColumns = ["id"],
            childColumns = ["bankAccountId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Company::class,
            parentColumns = ["companyId"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isLinkedToBank: Boolean,
    val bankAccountId: Int?,
    val companyId: Int?,
    val cardNumber: String,
    val expiryMonth: Byte,
    val expiryYear: Byte,
    val cvv: String,
    val nameOnCard: String,
    val cardNetwork: CardNetwork,
    val cardAlias: String?,
    val cardType: CardType,
) : Serializable {

    companion object {

        fun emptyCard(): Card {
            return Card(
                -1,
                false,
                null,
                null,
                "",
                14,
                1,
                "",
                "",
                CardNetwork.Unknown,
                null,
                CardType.None
            )
        }

    }

    @Throws(InvalidCardException::class)
    fun isValidCard(): Boolean {
        if (bankAccountId == null && companyId == null) {
            throw InvalidCardException("Please link card with issuer")
        }
        if (cardNumber.isEmpty() || cardNumber.length < 15) {
            throw InvalidCardException("Enter full card number")
        }
        if (expiryMonth < 1 || expiryMonth > 12) {
            throw InvalidCardException("Enter valid expiry month")
        }
        if (expiryYear.toString().length < 2) {
            throw InvalidCardException("Enter valid expiry year")
        }
        if (cvv.length != 3) {
            throw InvalidCardException("Enter valid cvv")
        }
        if (nameOnCard.isEmpty() || nameOnCard.length < 3) {
            throw InvalidCardException("Enter full name")
        }
        return true
    }
}
