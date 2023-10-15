package com.harshnandwani.digitaltijori.domain.repository

class FakeBackupRepository : BackupRepository {
    override fun saveBackup(data: String) {
        println("Backup saved!")
    }
}
