package com.harshnandwani.digitaltijori.data.util

import com.harshnandwani.digitaltijori.data.local.entity.CardEntity
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.accountWithBank2
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.accountWithBank3
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCards
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCardsHasCredentials
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.cardIssuer
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.CardType

object DummyCards {

    val cardWithInvalidCompany = CardEntity(
        companyId = -1,
        isLinkedToBank = false,
        bankAccountId = null,
        cardNumber = "111111111",
        expiryMonth = 1,
        expiryYear = 26,
        cvv = "123",
        nameOnCard = "Invalid issuer card",
        variant = "Dummy",
        cardNetwork = CardNetwork.Unknown,
        pin = "1234",
        cardAlias = "alias",
        cardType = CardType.Other
    )

    val cardWithInvalidAccount = CardEntity(
        companyId = cardIssuer.companyId,
        isLinkedToBank = true,
        bankAccountId = -1,
        cardNumber = "111111111",
        expiryMonth = 1,
        expiryYear = 26,
        cvv = "123",
        nameOnCard = "invalid account card",
        variant = "Dummy",
        cardNetwork = CardNetwork.Unknown,
        pin = "1234",
        cardAlias = "alias",
        cardType = CardType.Other
    )

    /*
    * Issuer 1 - cardIssuer
    * Issuer 2 - bankIssuesCards
    * Issuer 3 - bankIssuesCardsHasCredentials
    * */

    val cardWithIssuer1 = CardEntity(
        cardId = 1,
        companyId = cardIssuer.companyId,
        isLinkedToBank = false,
        bankAccountId = null,
        cardNumber = "111111111",
        expiryMonth = 1,
        expiryYear = 26,
        cvv = "123",
        nameOnCard = "Issuer 1 Card 1",
        variant = "Dummy",
        cardNetwork = CardNetwork.Unknown,
        pin = "1234",
        cardAlias = "alias1",
        cardType = CardType.Other
    )

    val card2WithIssuer1 = CardEntity(
        cardId = 2,
        companyId = cardIssuer.companyId,
        isLinkedToBank = false,
        bankAccountId = null,
        cardNumber = "2222222222",
        expiryMonth = 1,
        expiryYear = 26,
        cvv = "123",
        nameOnCard = "Issuer 1 Card 2",
        variant = "Dummy",
        cardNetwork = CardNetwork.Unknown,
        pin = "1234",
        cardAlias = "alias2",
        cardType = CardType.Other
    )

    val cardWithIssuer2 = CardEntity(
        cardId = 3,
        companyId = bankIssuesCards.companyId,
        isLinkedToBank = false,
        bankAccountId = null,
        cardNumber = "33333333333",
        expiryMonth = 1,
        expiryYear = 26,
        cvv = "123",
        nameOnCard = "Issuer 2 Card 1",
        variant = "Dummy",
        cardNetwork = CardNetwork.Unknown,
        pin = "1234",
        cardAlias = "alias1",
        cardType = CardType.Other
    )

    val card2WithIssuer2 = CardEntity(
        cardId = 4,
        companyId = bankIssuesCards.companyId,
        isLinkedToBank = true,
        bankAccountId = accountWithBank2.bankAccountId,
        cardNumber = "44444444444444",
        expiryMonth = 1,
        expiryYear = 26,
        cvv = "123",
        nameOnCard = "Issuer 2 Card 2",
        variant = "Dummy",
        cardNetwork = CardNetwork.Unknown,
        pin = "1234",
        cardAlias = "alias2",
        cardType = CardType.Other
    )

    val cardWithIssuer3 = CardEntity(
        cardId = 5,
        companyId = bankIssuesCardsHasCredentials.companyId,
        isLinkedToBank = false,
        bankAccountId = null,
        cardNumber = "55555555555",
        expiryMonth = 1,
        expiryYear = 26,
        cvv = "123",
        nameOnCard = "Issuer 3 Card 1",
        variant = "Dummy",
        cardNetwork = CardNetwork.Unknown,
        pin = "1234",
        cardAlias = "alias1",
        cardType = CardType.Other
    )

    val card2WithIssuer3 = CardEntity(
        cardId = 6,
        companyId = bankIssuesCardsHasCredentials.companyId,
        isLinkedToBank = true,
        bankAccountId = accountWithBank3.bankAccountId,
        cardNumber = "666666666666",
        expiryMonth = 1,
        expiryYear = 26,
        cvv = "123",
        nameOnCard = "Issuer 3 Card 2",
        variant = "Dummy",
        cardNetwork = CardNetwork.Unknown,
        pin = "1234",
        cardAlias = "alias2",
        cardType = CardType.Other
    )
}
