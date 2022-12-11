package com.harshnandwani.digitaltijori.domain.use_case.bank_account

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.util.InvalidBankAccountException
import kotlin.jvm.Throws

class ValidateBankAccountUseCase {
    @Throws(InvalidBankAccountException::class)
    operator fun invoke(account: BankAccount): Boolean {
        account.apply {
            if (linkedCompany.companyId <= 0) {
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
}
