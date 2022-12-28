package com.harshnandwani.digitaltijori.domain.use_case.bank_account

import com.harshnandwani.digitaltijori.domain.use_case.bank_account.util.DummyAccounts.accountWithInvalidCompanyId
import com.harshnandwani.digitaltijori.domain.util.InvalidBankAccountException
import org.junit.Assert.assertThrows
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.util.DummyAccounts.accountWithInvalidAccountNumber
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.util.DummyAccounts.accountWithInvalidHolderName
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.util.DummyAccounts.accountWithInvalidIFSC
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.util.DummyAccounts.validAccount


class ValidateBankAccountUseCaseTest {

    val sut = ValidateBankAccountUseCase()

    @Test
    fun `WHEN Company id is less than or equal to 0 THEN throw InvalidBankAccountException`() {
        val exception = assertThrows(InvalidBankAccountException::class.java) {
            sut(accountWithInvalidCompanyId)
        }

        assertThat(exception).hasMessageThat().matches("Select bank")
    }

    @Test
    fun `WHEN account number is less than 8 digits THEN throw InvalidBankAccountException`() {
        val exception = assertThrows(InvalidBankAccountException::class.java) {
            sut(accountWithInvalidAccountNumber)
        }

        assertThat(exception).hasMessageThat().matches("Enter at least 8 digit account number")
    }

    @Test
    fun `WHEN ifsc is empty THEN throw InvalidBankAccountException`() {
        val exception = assertThrows(InvalidBankAccountException::class.java) {
            sut(accountWithInvalidIFSC)
        }

        assertThat(exception).hasMessageThat().matches("IFSC cannot be empty")
    }

    @Test
    fun `WHEN holder name is empty THEN throw InvalidBankAccountException`() {
        val exception = assertThrows(InvalidBankAccountException::class.java) {
            sut(accountWithInvalidHolderName)
        }

        assertThat(exception).hasMessageThat().matches("Holder name cannot be empty")
    }

    @Test
    fun `WHEN everything is valid THEN return true`() {
        val result = sut(validAccount)
        assertThat(result).isTrue()
    }
}
