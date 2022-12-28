package com.harshnandwani.digitaltijori.domain.use_case.bank_account.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.util.DummyCompanies.bank
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.util.DummyCompanies.invalidCompany

object DummyAccounts {
    val accountWithInvalidCompanyId = BankAccount(
        1,
        invalidCompany,
        "",
        "",
        "",
        null,
        null
    )

    val accountWithInvalidAccountNumber = BankAccount(
        1,
        bank,
        "",
        "1",
        "",
        null,
        null
    )

    val accountWithInvalidIFSC = BankAccount(
        1,
        bank,
        "",
        "1234567890",
        "",
        null,
        null
    )

    val accountWithInvalidHolderName = BankAccount(
        1,
        bank,
        "",
        "1234567890",
        "IFSC000",
        null,
        null
    )

    val validAccount = BankAccount(
        1,
        bank,
        "Name",
        "1234567890",
        "IFSC000",
        null,
        null
    )
}
