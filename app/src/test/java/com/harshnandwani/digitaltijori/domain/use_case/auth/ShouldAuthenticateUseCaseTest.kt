package com.harshnandwani.digitaltijori.domain.use_case.auth

import com.google.common.truth.Truth.assertThat
import com.harshnandwani.digitaltijori.domain.repository.FakePreferenceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ShouldAuthenticateUseCaseTest {

    private val fakePreferenceRepository = FakePreferenceRepository()
    private val sut = ShouldAuthenticateUseCase(repository = fakePreferenceRepository)

    @Test
    fun `WHEN last auth timestamp is -1 THEN return false`() = runTest {
        fakePreferenceRepository.setAuthenticatedTimestamp(-1)
        val result = sut()
        assertThat(result).isFalse()
    }

    @Test
    fun `WHEN last auth timestamp is below 3 minutes THEN return false`() = runTest {

        val timeNow = System.currentTimeMillis()
        val oneMinute = 1000 * 60
        val twoMinutes = 1000 * 60 * 2
        val threeMinutes = 1000 * 60 * 3
        val timeBufferMillis = 10

        fakePreferenceRepository.setAuthenticatedTimestamp(timeNow)
        val resultNow = sut()
        assertThat(resultNow).isFalse()

        fakePreferenceRepository.setAuthenticatedTimestamp(timeNow - oneMinute)
        val resultAfterOneMin = sut()
        assertThat(resultAfterOneMin).isFalse()

        fakePreferenceRepository.setAuthenticatedTimestamp(timeNow - twoMinutes)
        val resultAfterTwoMin = sut()
        assertThat(resultAfterTwoMin).isFalse()

        fakePreferenceRepository.setAuthenticatedTimestamp(timeNow - threeMinutes + timeBufferMillis)
        val resultAfterAlmostThreeMin = sut()
        assertThat(resultAfterAlmostThreeMin).isFalse()
    }

    @Test
    fun `WHEN last auth timestamp is 3 minutes or above THEN return true`() = runTest {
        val timeNow = System.currentTimeMillis()
        val threeMinutes = 1000 * 60 * 3

        fakePreferenceRepository.setAuthenticatedTimestamp(timeNow - threeMinutes)
        val resultAfterExactlyThreeMinutes = sut()
        assertThat(resultAfterExactlyThreeMinutes).isTrue()

        fakePreferenceRepository.setAuthenticatedTimestamp(timeNow - threeMinutes - (0..Int.MAX_VALUE).random())
        val resultAfterMoreThanThreeMinutes = sut()
        assertThat(resultAfterMoreThanThreeMinutes).isTrue()
    }

}
