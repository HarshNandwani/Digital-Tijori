package com.harshnandwani.digitaltijori.domain.use_case.auth

import com.harshnandwani.digitaltijori.domain.repository.FakePreferenceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any

class SetAuthenticatedUseCaseTest {

    private val fakePreferenceRepository = Mockito.spy(FakePreferenceRepository())
    private val sut = ShouldAuthenticateUseCase(repository = fakePreferenceRepository)

    @Test
    fun `On invoke call PreferenceRepository method setAuthenticatedTimestamp`() = runTest {
        sut()
        Mockito.verify(fakePreferenceRepository).setAuthenticatedTimestamp(any())
    }

}
