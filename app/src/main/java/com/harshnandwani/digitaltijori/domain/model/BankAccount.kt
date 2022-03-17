package com.harshnandwani.digitaltijori.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.harshnandwani.digitaltijori.domain.util.InvalidBankAccountException
import java.io.Serializable
import kotlin.jvm.Throws

@Entity(
    foreignKeys = [ForeignKey(
        entity = Company::class,
        parentColumns = ["id"],
        childColumns = ["bankId"],
        onDelete = ForeignKey.RESTRICT
    )]
)
data class BankAccount(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bankId: Int,
    val holderName: String,
    val accountNumber: String,
    val ifsc: String,
    val phoneNumber: String?,
    val alias: String?
) : Serializable {

    @Throws(InvalidBankAccountException::class)
    fun isValidBankAccount(): Boolean {
        if (bankId == -1) {
            throw InvalidBankAccountException("Select bank")
        }
        if (accountNumber.isEmpty() || accountNumber.length < 8) {
            throw InvalidBankAccountException("Enter at least 8 digit account number")
        }
        if (ifsc.isEmpty()) {
            throw InvalidBankAccountException("IFSC cannot be empty")
        }
        if (holderName.isEmpty()) {
            throw InvalidBankAccountException("Holder name cannot be empty")
        }
        return true
    }
}
