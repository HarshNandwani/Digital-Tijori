package com.harshnandwani.digitaltijori.domain.use_case.preference

import com.harshnandwani.digitaltijori.domain.repository.PreferenceRepository

class SetDoNotShowAboutAppUseCase(
    private val repository: PreferenceRepository
) {

    suspend operator fun invoke() {
        repository.setShowAboutApp(false)
    }

}
