package com.harshnandwani.digitaltijori.domain.use_case.backup_restore

import com.google.gson.Gson
import com.harshnandwani.digitaltijori.domain.model.AllData
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.AddBankAccountUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.AddCardUseCase
import com.harshnandwani.digitaltijori.domain.use_case.credential.AddCredentialUseCase

class RestoreUseCase(
    private val encryptDecryptData: EncryptDecryptDataUseCase,
    private val addBankAccount: AddBankAccountUseCase,
    private val addCard: AddCardUseCase,
    private val addCredential: AddCredentialUseCase
) {
    suspend operator fun invoke(encryptedBackup: String, key: String) {
        val decryptedBackup = encryptDecryptData(Mode.MODE_DECRYPT, key, encryptedBackup)
        val allData: AllData = Gson().fromJson(decryptedBackup, AllData::class.java)

        allData.bankAccounts.forEach { addBankAccount(it) }
        allData.cards.forEach { addCard(it) }
        allData.credential.forEach { addCredential(it) }

    }
}
