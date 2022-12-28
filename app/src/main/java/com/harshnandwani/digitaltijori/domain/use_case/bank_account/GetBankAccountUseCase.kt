package com.harshnandwani.digitaltijori.domain.use_case.bank_account

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository

class GetBankAccountUseCase(
    private val repository: BankAccountRepository
) {
    suspend operator fun invoke(id: Int): BankAccount? {
        return repository.get(id)
    }
}
