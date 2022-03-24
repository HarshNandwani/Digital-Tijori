package com.harshnandwani.digitaltijori.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.harshnandwani.digitaltijori.domain.util.InvalidCredentialException
import java.io.Serializable
import kotlin.jvm.Throws

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = BankAccount::class,
            parentColumns = ["bankAccountId"],
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
data class Credential(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val isLinkedToBank : Boolean,
    val bankAccountId : Int?,
    val companyId: Int?
) : Serializable {

    companion object {
        fun emptyCredential(): Credential {
            return Credential(
                username = "",
                password = "",
                isLinkedToBank = false,
                bankAccountId = null,
                companyId = null
            )
        }
    }

    @Throws(InvalidCredentialException::class)
    fun isValid(): Boolean {
        if (isLinkedToBank && (bankAccountId == null || bankAccountId == -1)) {
            throw InvalidCredentialException("Select entity")
        }
        if (!isLinkedToBank && (companyId == null || companyId == -1)) {
            throw InvalidCredentialException("Select entity")
        }
        if (username.isEmpty()) {
            throw InvalidCredentialException("Enter username")
        }
        if (password.isEmpty()) {
            throw InvalidCredentialException("Enter password")
        }
        return true
    }

}
