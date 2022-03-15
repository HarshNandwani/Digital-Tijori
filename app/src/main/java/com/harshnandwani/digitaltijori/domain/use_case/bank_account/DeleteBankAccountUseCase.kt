package com.harshnandwani.digitaltijori.domain.use_case.bank_account

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository

class DeleteBankAccountUseCase(
    private val repository: BankAccountRepository
) {
    suspend operator fun invoke(account: BankAccount) {
        repository.delete(account)
    }
}
