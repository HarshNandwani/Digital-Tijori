package com.harshnandwani.digitaltijori.domain.use_case.bank_account

import android.database.sqlite.SQLiteConstraintException
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import kotlin.jvm.Throws

class DeleteBankAccountUseCase(
    private val repository: BankAccountRepository
) {

    @Throws(SQLiteConstraintException::class)
    suspend operator fun invoke(account: BankAccount) {
        repository.delete(account)
    }

}
