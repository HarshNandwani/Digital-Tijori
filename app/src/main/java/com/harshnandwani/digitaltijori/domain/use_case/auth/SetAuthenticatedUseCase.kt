package com.harshnandwani.digitaltijori.domain.use_case.auth

import com.harshnandwani.digitaltijori.domain.repository.PreferenceRepository
import java.time.Clock

class SetAuthenticatedUseCase(
    private val repository: PreferenceRepository
) {

    suspend operator fun invoke() {
        repository.setAuthenticatedTimestamp(Clock.systemDefaultZone().millis())
    }

}
