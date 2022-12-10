package com.harshnandwani.digitaltijori.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.harshnandwani.digitaltijori.domain.model.Credential

@Entity(
    tableName = "Credential",
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
data class CredentialEntity(
    @PrimaryKey(autoGenerate = true)
    val credentialId: Int = 0,
    val username: String,
    val password: String,
    val isLinkedToBank: Boolean,
    val bankAccountId: Int?,
    val companyId: Int?,
    val alias: String?
) {
    companion object {
        fun toEntity(credential: Credential) = CredentialEntity(
            credential.credentialId,
            credential.username,
            credential.password,
            credential.isLinkedToBank,
            credential.bankAccountId,
            credential.companyId,
            credential.alias
        )
    }

    fun toDomain() = Credential(
        credentialId,
        username,
        password,
        isLinkedToBank,
        bankAccountId,
        companyId,
        alias
    )
}
