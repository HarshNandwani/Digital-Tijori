package com.harshnandwani.digitaltijori.domain.use_case.bank_account

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import com.harshnandwani.digitaltijori.domain.util.InvalidBankAccountException
import kotlin.jvm.Throws

class UpdateBankAccountUseCase(
    private val repository: BankAccountRepository,
    private val isValid: ValidateBankAccountUseCase
) {
    @Throws(InvalidBankAccountException::class)
    suspend operator fun invoke(account: BankAccount) {
        if (isValid(account)) {
            repository.update(account)
        }
    }
}
