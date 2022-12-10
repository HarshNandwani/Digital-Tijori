package com.harshnandwani.digitaltijori.domain.model

import com.harshnandwani.digitaltijori.domain.util.InvalidBankAccountException
import java.io.Serializable
import kotlin.jvm.Throws

data class BankAccount(
    val bankAccountId: Int = 0,
    val companyId: Int,
    val holderName: String,
    val accountNumber: String,
    val ifsc: String,
    val phoneNumber: String?,
    val alias: String?
) : Serializable {

    @Throws(InvalidBankAccountException::class)
    fun isValidBankAccount(): Boolean {
        if (companyId == -1) {
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
