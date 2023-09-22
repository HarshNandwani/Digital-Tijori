package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.domain.repository.BackupRepository
import java.io.File
import java.io.FileOutputStream

class BackupRepositoryImpl(
    private val filesDir: File
) : BackupRepository {
    private val directoryName = "backups"
    private val backupFileName = "digital_tijori_encrypted_backup.txt"

    override fun saveBackup(data: String) {
        val myDir = File(filesDir, directoryName)
        myDir.mkdir()

        val backupFile = File(myDir.path, backupFileName)

        if (backupFile.exists()) {
            backupFile.delete()
        } else {
            backupFile.createNewFile()
        }
        FileOutputStream(backupFile).use {
            it.write(data.toByteArray())
            it.close()
        }
    }
}
