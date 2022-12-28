package com.harshnandwani.digitaltijori.data.local.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.CardType
import com.harshnandwani.digitaltijori.domain.util.ColorScheme

@Entity(
    tableName = "Card",
    foreignKeys = [
        ForeignKey(
            entity = BankAccountEntity::class,
            parentColumns = ["bankAccountId"],
            childColumns = ["bankAccountId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = CompanyEntity::class,
            parentColumns = ["companyId"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index("bankAccountId"),
        Index("companyId")
    ]
)
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val cardId: Int = 0,
    val isLinkedToBank: Boolean,
    val bankAccountId: Int?,
    val companyId: Int?,
    val cardNumber: String,
    val expiryMonth: Byte,
    val expiryYear: Byte,
    val cvv: String,
    val nameOnCard: String,
    val variant: String?,
    val cardNetwork: CardNetwork,
    val pin: String,
    val colorScheme: ColorScheme = ColorScheme(
        Color(0xFF5FA2D7).toArgb(),
        Color(0xFF9CBDE4).toArgb()
    ),
    val cardAlias: String?,
    val cardType: CardType,
) {
    companion object {
        fun toEntity(card: Card) = CardEntity(
            card.cardId,
            card.isLinkedToBank,
            card.bankAccount?.bankAccountId,
            card.company.companyId,
            card.cardNumber,
            card.expiryMonth,
            card.expiryYear,
            card.cvv,
            card.nameOnCard,
            card.variant,
            card.cardNetwork,
            card.pin,
            card.colorScheme,
            card.cardAlias,
            card.cardType
        )
    }

    fun toDomain(bankAccount: BankAccount?, issuer: Company) = Card(
        cardId,
        isLinkedToBank,
        bankAccount,
        bankAccount?.linkedCompany ?: issuer,
        cardNumber,
        expiryMonth,
        expiryYear,
        cvv,
        nameOnCard,
        variant,
        cardNetwork,
        pin,
        colorScheme,
        cardAlias,
        cardType
    )
}
