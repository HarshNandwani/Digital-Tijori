package com.harshnandwani.digitaltijori.domain.use_case.backup_restore

import com.harshnandwani.digitaltijori.domain.repository.BackupRepository
import java.lang.Exception
import kotlin.jvm.Throws

class CreateBackupUseCase(
    private val getAllDataInJson: GetAllDataInJsonUseCase,
    private val encryptDecryptData: EncryptDecryptDataUseCase,
    private val backupAndRestoreRepository: BackupRepository
) {
    @Throws(Exception::class)
    suspend operator fun invoke(key: String) {
        val dataInJson = getAllDataInJson()
        val encryptedData = encryptDecryptData(Mode.MODE_ENCRYPT, key, dataInJson)
        backupAndRestoreRepository.saveBackup(encryptedData)
    }
}
