package com.harshnandwani.digitaltijori.domain.use_case.bank_account

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository

class UpdateBankAccountUseCase(
    private val repository: BankAccountRepository
) {
    suspend operator fun invoke(account: BankAccount){
        if(account.isValidBankAccount()){
            repository.update(account)
        }
    }
}
