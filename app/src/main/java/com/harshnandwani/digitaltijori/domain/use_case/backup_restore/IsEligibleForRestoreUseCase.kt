package com.harshnandwani.digitaltijori.domain.use_case.backup_restore

import com.harshnandwani.digitaltijori.domain.repository.PreferenceRepository

class IsEligibleForRestoreUseCase(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.isAppOpenedFirstTime()
    }
}
