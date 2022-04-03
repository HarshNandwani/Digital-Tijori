package com.harshnandwani.digitaltijori.domain.use_case.auth

import com.harshnandwani.digitaltijori.domain.repository.PreferenceRepository
import java.time.Clock

class ShouldAuthenticateUseCase(
    private val repository: PreferenceRepository
) {

    private val AUTH_GRACE = 180000 // 3 minutes

    suspend operator fun invoke(): Boolean {
        val lastAuthenticated = repository.getLastAuthenticatedTimestamp()
        val timeNow = Clock.systemDefaultZone().millis()
        return if (lastAuthenticated == -1L) {
            repository.setAuthenticatedTimestamp(timeNow - AUTH_GRACE)
            false
        } else {
            timeNow - lastAuthenticated > AUTH_GRACE
        }
    }

}
