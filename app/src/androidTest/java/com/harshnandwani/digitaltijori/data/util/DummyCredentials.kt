package com.harshnandwani.digitaltijori.data.util

import com.harshnandwani.digitaltijori.data.util.DummyAccounts.accountWithBank3
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCardsHasCredentials
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.entity
import com.harshnandwani.digitaltijori.domain.model.Credential

object DummyCredentials {

    val credsWithInvalidCompany = Credential(
        companyId = -1,
        isLinkedToBank = false,
        bankAccountId = null,
        username = "test_credential",
        password = "Pass",
        alias = "dummy_alias"
    )

    val credsWithInvalidAccount = Credential(
        companyId = entity.companyId,
        isLinkedToBank = true,
        bankAccountId = -1,
        username = "test_credential",
        password = "Pass",
        alias = "dummy_alias"
    )

    /*
    * Entity 1 - entity
    * Entity 2 - bankIssuesCardsHasCredentials
    * */

    val credsWithEntity1 = Credential(
        credentialId = 1,
        companyId = entity.companyId,
        isLinkedToBank = false,
        bankAccountId = null,
        username = "test_credential_1",
        password = "Pass111",
        alias = "dummy_alias_1"
    )

    val creds2WithEntity1 = Credential(
        credentialId = 2,
        companyId = entity.companyId,
        isLinkedToBank = false,
        bankAccountId = null,
        username = "test_credential_2",
        password = "Pass222",
        alias = "dummy_alias_2"
    )

    val credsWithEntity2 = Credential(
        credentialId = 3,
        companyId = bankIssuesCardsHasCredentials.companyId,
        isLinkedToBank = false,
        bankAccountId = null,
        username = "test_credential_3",
        password = "Pass333",
        alias = "dummy_alias_3"
    )

    val creds2WithEntity2 = Credential(
        credentialId = 4,
        companyId = bankIssuesCardsHasCredentials.companyId,
        isLinkedToBank = true,
        bankAccountId = accountWithBank3.bankAccountId,
        username = "test_credential_4",
        password = "Pass444",
        alias = "dummy_alias_4"
    )

    val creds3WithEntity2 = Credential(
        credentialId = 5,
        companyId = bankIssuesCardsHasCredentials.companyId,
        isLinkedToBank = true,
        bankAccountId = accountWithBank3.bankAccountId,
        username = "test_credential_4",
        password = "Pass444",
        alias = "dummy_alias_4"
    )
}
