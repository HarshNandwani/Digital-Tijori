package com.harshnandwani.digitaltijori.domain.use_case.bank_account

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import com.harshnandwani.digitaltijori.domain.util.InvalidBankAccountException
import kotlin.jvm.Throws

class AddBankAccountUseCase(
    private val repository: BankAccountRepository
) {
    @Throws(InvalidBankAccountException::class)
    suspend operator fun invoke(account: BankAccount): Long {
        if(account.isValidBankAccount()){
            return repository.add(account)
        }
        return -1L
    }
}
