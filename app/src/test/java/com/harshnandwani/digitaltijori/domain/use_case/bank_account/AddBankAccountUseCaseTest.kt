package com.harshnandwani.digitaltijori.domain.use_case.bank_account

import com.google.common.truth.Truth.assertThat
import com.harshnandwani.digitaltijori.domain.repository.FakeBankAccountRepository
import com.harshnandwani.digitaltijori.domain.util.DummyAccounts
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class AddBankAccountUseCaseTest {
    private val fakeBankAccountRepository = Mockito.spy(FakeBankAccountRepository())
    private val isValidBankAccountUseCase = Mockito.spy(ValidateBankAccountUseCase())
    private val sut = AddBankAccountUseCase(fakeBankAccountRepository, isValidBankAccountUseCase)

    @Test
    fun `WHEN invoked THEN call ValidateBankAccountUseCase`() = runTest {
        sut(DummyAccounts.validAccount)
        verify(isValidBankAccountUseCase).invoke(DummyAccounts.validAccount)
    }

    @Test
    fun `WHEN invoked with valid bank account THEN call bank account repository add method`() =
        runTest {
            val result = sut(DummyAccounts.validAccount)
            verify(fakeBankAccountRepository).add(DummyAccounts.validAccount)
            assertThat(result).isAtLeast(1)
        }

}
