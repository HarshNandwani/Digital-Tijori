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
            parentColumns = ["id"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val isLinkedToBank: Boolean,
    val bankAccountId: Int?,
    val companyId: Int?,
    val cardNumber: String,
    val expiryMonth: Byte,
    val expiryYear: Byte,
    val cvv: Short,
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
                1,
                "",
                CardNetwork.Unknown,
                null,
                CardType.None
            )
        }

    }

    @Throws(InvalidCardException::class)
    fun isValidCard(): Boolean {
        if (this.bankAccountId == null && this.companyId == null) {
            throw InvalidCardException("Please link card")
        }
        if (this.cardNumber.isEmpty()) {
            throw InvalidCardException("Enter full card number")
        }
        if (this.expiryMonth < 1 || this.expiryMonth > 12) {
            throw InvalidCardException("Enter valid expiry month")
        }
        if (this.expiryYear.toString().length < 2) {
            throw InvalidCardException("Enter valid expiry year")
        }
        if (this.cvv.toString().length < 3) {
            throw InvalidCardException("Enter valid cvv")
        }
        if (this.nameOnCard.isEmpty()) {
            throw InvalidCardException("Enter name")
        }
        return true
    }
}
