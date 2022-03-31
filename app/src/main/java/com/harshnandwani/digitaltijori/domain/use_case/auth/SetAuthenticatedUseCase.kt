package com.harshnandwani.digitaltijori.domain.use_case.auth

import com.harshnandwani.digitaltijori.domain.repository.AuthRepository
import java.time.Clock

class SetAuthenticatedUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() {
        authRepository.setAuthenticatedTimestamp(Clock.systemDefaultZone().millis())
    }

}
