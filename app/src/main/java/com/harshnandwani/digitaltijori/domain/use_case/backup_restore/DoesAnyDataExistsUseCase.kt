package com.harshnandwani.digitaltijori.domain.use_case.backup_restore

import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository

class DoesAnyDataExistsUseCase(
    private val bankAccountRepository: BankAccountRepository,
    private val cardRepository: CardRepository,
    private val credentialRepository: CredentialRepository
) {
    suspend operator fun invoke(): Boolean {
        return bankAccountRepository.dataExists()
                || cardRepository.dataExists()
                || credentialRepository.dataExists()
    }
}
