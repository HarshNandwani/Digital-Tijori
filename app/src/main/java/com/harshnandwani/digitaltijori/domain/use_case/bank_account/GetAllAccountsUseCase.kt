package com.harshnandwani.digitaltijori.domain.use_case.bank_account

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import kotlinx.coroutines.flow.Flow

class GetAllAccountsUseCase(
    private val repository: BankAccountRepository
) {
    operator fun invoke(): Flow<List<BankAccount>> {
        return repository.getAll()
    }
}
