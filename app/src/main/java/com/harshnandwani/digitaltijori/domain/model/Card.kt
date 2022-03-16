package com.harshnandwani.digitaltijori.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.CardType
import java.io.Serializable

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

}
