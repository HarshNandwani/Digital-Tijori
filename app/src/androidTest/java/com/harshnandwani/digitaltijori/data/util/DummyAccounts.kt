package com.harshnandwani.digitaltijori.data.util

import com.harshnandwani.digitaltijori.data.local.entity.BankAccountEntity
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bank
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCards
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCardsHasCredentials

object DummyAccounts {

    val account = BankAccountEntity(
        companyId = -1,
        holderName = "Bank 1 Account 1",
        accountNumber = "11111111111",
        ifsc = "IFSC",
        phoneNumber = "0000000001",
        alias = "alias"
    )

    val accountWithBank1 = BankAccountEntity(
        bankAccountId = 1,
        companyId = bank.companyId,
        holderName = "Bank 1 Account 1",
        accountNumber = "B1A1_11111111111",
        ifsc = "IFSC_BANK_1",
        phoneNumber = "0000000001",
        alias = "alias_of_account_1"
    )

    val account2WithBank1 = BankAccountEntity(
        bankAccountId = 2,
        companyId = bank.companyId,
        holderName = "Bank 1 Account 2",
        accountNumber = "B1A2_2222222222",
        ifsc = "IFSC_BANK_1",
        phoneNumber = "0000000002",
        alias = "alias_of_account_2"
    )
    
    val accountWithBank2 = BankAccountEntity(
        bankAccountId = 3,
        companyId = bankIssuesCards.companyId,
        holderName = "Bank 2 Account 1",
        accountNumber = "B2A1_3333333333",
        ifsc = "IFSC_BANK_2",
        phoneNumber = "0000000003",
        alias = "alias_of_account_3"
    )
    
    val account2WithBank2 = BankAccountEntity(
        bankAccountId = 4,
        companyId = bankIssuesCards.companyId,
        holderName = "Bank 2 Account 2",
        accountNumber = "B2A2_44444444",
        ifsc = "IFSC_BANK_2",
        phoneNumber = "0000000004",
        alias = "alias_of_account_4"
    )
    
    val accountWithBank3 = BankAccountEntity(
        bankAccountId = 5,
        companyId = bankIssuesCardsHasCredentials.companyId,
        holderName = "Bank 3 Account 1",
        accountNumber = "B3A1_5555555555",
        ifsc = "IFSC_BANK_3",
        phoneNumber = "0000000005",
        alias = "alias_of_account_5"
    )

    val account2WithBank3 = BankAccountEntity(
        bankAccountId = 6,
        companyId = bankIssuesCardsHasCredentials.companyId,
        holderName = "Bank 3 Account 2",
        accountNumber = "B3A2_6666666666",
        ifsc = "IFSC_BANK_3",
        phoneNumber = "0000000006",
        alias = "alias_of_account_6"
    )
}
