package com.harshnandwani.digitaltijori.domain.use_case.backup_restore

import com.harshnandwani.digitaltijori.domain.repository.PreferenceRepository

class IsEligibleForRestoreUseCase(
    private val preferenceRepository: PreferenceRepository,
    private val doesAnyDataExists: DoesAnyDataExistsUseCase
) {
    suspend operator fun invoke(): Boolean {
        return preferenceRepository.isAppOpenedFirstTime() && doesAnyDataExists().not()
    }
}
