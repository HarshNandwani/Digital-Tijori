package com.harshnandwani.digitaltijori.domain.repository

import java.io.File

interface BackupRepository {
    fun saveBackup(data: String): File
}
