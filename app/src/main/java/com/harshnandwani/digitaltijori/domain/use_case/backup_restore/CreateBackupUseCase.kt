package com.harshnandwani.digitaltijori.domain.use_case.backup_restore

import com.harshnandwani.digitaltijori.domain.repository.BackupRepository
import java.io.File
import java.lang.Exception
import kotlin.jvm.Throws

class CreateBackupUseCase(
    private val getAllDataInJson: GetAllDataInJsonUseCase,
    private val encryptDecryptData: EncryptDecryptDataUseCase,
    private val backupAndRestoreRepository: BackupRepository
) {
    @Throws(Exception::class)
    suspend operator fun invoke(key: String, saveBackupFile: (File) -> Unit) {
        val dataInJson = getAllDataInJson()
        val encryptedData = encryptDecryptData(Mode.MODE_ENCRYPT, key, dataInJson)
        val backupFile = backupAndRestoreRepository.saveBackup(encryptedData)
        saveBackupFile(backupFile)
    }
}
