package com.harshnandwani.digitaltijori.domain.use_case.auth

import com.harshnandwani.digitaltijori.domain.repository.AuthRepository
import java.time.Clock

class ShouldAuthenticateUseCase(
    private val authRepository: AuthRepository
) {

    private val AUTH_GRACE = 180000 // 3 minutes

    suspend operator fun invoke(): Boolean {
        val lastAuthenticated = authRepository.getLastAuthenticatedTimestamp()
        val timeNow = Clock.systemDefaultZone().millis()
        return if (lastAuthenticated == -1L) {
            authRepository.setAuthenticatedTimestamp(timeNow - AUTH_GRACE)
            false
        } else {
            timeNow - lastAuthenticated > AUTH_GRACE
        }
    }

}
