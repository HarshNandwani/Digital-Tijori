package com.harshnandwani.digitaltijori.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company

@Entity(
    tableName = "BankAccount",
    foreignKeys = [ForeignKey(
        entity = CompanyEntity::class,
        parentColumns = ["companyId"],
        childColumns = ["companyId"],
        onDelete = ForeignKey.RESTRICT
    )],
    indices = [
        Index("companyId")
    ]
)
data class BankAccountEntity(
    @PrimaryKey(autoGenerate = true)
    val bankAccountId: Int = 0,
    val companyId: Int,
    val holderName: String,
    val accountNumber: String,
    val ifsc: String,
    val phoneNumber: String?,
    val alias: String?
) {
    companion object {
        fun toEntity(account: BankAccount) = BankAccountEntity(
            account.bankAccountId,
            account.linkedCompany.companyId,
            account.holderName,
            account.accountNumber,
            account.ifsc,
            account.phoneNumber,
            account.alias
        )
    }

    fun toDomain(company: Company) =
        BankAccount(bankAccountId, company, holderName, accountNumber, ifsc, phoneNumber, alias)
}
