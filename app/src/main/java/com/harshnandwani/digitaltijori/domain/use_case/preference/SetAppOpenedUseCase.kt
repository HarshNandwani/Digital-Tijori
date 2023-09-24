package com.harshnandwani.digitaltijori.domain.use_case.preference

import com.harshnandwani.digitaltijori.domain.repository.PreferenceRepository

class SetAppOpenedUseCase(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke() {
        repository.setAppOpened()
    }
}
