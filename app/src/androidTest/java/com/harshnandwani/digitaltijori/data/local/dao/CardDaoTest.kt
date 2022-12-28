package com.harshnandwani.digitaltijori.data.local.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.harshnandwani.digitaltijori.data.local.DigitalTijoriDatabase
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.accountWithBank2
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.accountWithBank3
import com.harshnandwani.digitaltijori.data.util.DummyCards.card2WithIssuer1
import com.harshnandwani.digitaltijori.data.util.DummyCards.card2WithIssuer2
import com.harshnandwani.digitaltijori.data.util.DummyCards.card2WithIssuer3
import com.harshnandwani.digitaltijori.data.util.DummyCards.cardWithInvalidAccount
import com.harshnandwani.digitaltijori.data.util.DummyCards.cardWithInvalidCompany
import com.harshnandwani.digitaltijori.data.util.DummyCards.cardWithIssuer1
import com.harshnandwani.digitaltijori.data.util.DummyCards.cardWithIssuer2
import com.harshnandwani.digitaltijori.data.util.DummyCards.cardWithIssuer3
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCards
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCardsHasCredentials
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.cardIssuer
import com.harshnandwani.digitaltijori.domain.util.CardNetwork
import com.harshnandwani.digitaltijori.domain.util.CardType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CardDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DigitalTijoriDatabase
    private lateinit var dao: CardDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DigitalTijoriDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.cardDao
        runBlockingTest {
            addIssuersAndAccounts()
        }
    }

    @After
    fun closeDown() {
        database.close()
    }

    /*
    * Test to check the companyId foreign key constraint of Card
    * */
    @Test
    fun addCardWithInvalidIssuerTest() = runBlockingTest {
        try {
            dao.add(cardWithInvalidCompany)
        } catch (e: SQLiteConstraintException) {
            assertThat(e.message).contains("FOREIGN KEY constraint failed")
        }
        val allCards = dao.getAll().first()
        assertThat(allCards).doesNotContain(cardWithInvalidCompany)
    }

    /*
    * Test to check the bankAccountId foreign key constraint of Card
    * */
    @Test
    fun addCardWithInvalidBankAccountTest() = runBlockingTest {
        try {
            dao.add(cardWithInvalidAccount)
        } catch (e: SQLiteConstraintException) {
            assertThat(e.message).contains("FOREIGN KEY constraint failed")
        }
        val allCards = dao.getAll().first()
        assertThat(allCards).doesNotContain(cardWithInvalidAccount)
    }

    @Test
    fun addAndGetCardsTest() = runBlockingTest {
        dao.add(cardWithIssuer1)
        dao.add(card2WithIssuer1)
        val allCards = dao.getAll().first()
        assertThat(allCards).containsExactly(cardWithIssuer1, card2WithIssuer1)
    }

    @Test
    fun addCardsFromDifferentIssuers() = runBlockingTest {
        addAllCards()
        val allCards = dao.getAll().first()
        assertThat(allCards).containsExactly(
            cardWithIssuer1,
            card2WithIssuer1,
            cardWithIssuer2,
            card2WithIssuer2,
            cardWithIssuer3,
            card2WithIssuer3,
        )
    }

    @Test
    fun getCardTest() = runBlockingTest {
        dao.add(cardWithIssuer1)
        assertThat(dao.get(cardWithIssuer1.cardId)).isEqualTo(cardWithIssuer1)
    }

    @Test
    fun updateCardTest() = runBlockingTest {
        dao.add(cardWithIssuer2)
        val updatedCard = cardWithIssuer2.copy(
            isLinkedToBank = true,
            bankAccountId = accountWithBank2.bankAccountId,
            cardNumber = "1312432553465",
            expiryMonth = 4,
            expiryYear = 25,
            cvv = "000",
            nameOnCard = "Updated card",
            variant = "updated variant",
            cardNetwork = CardNetwork.MasterCard,
            pin = "5555",
            cardAlias = "Updated alias",
            cardType = CardType.Debit
        )
        dao.update(updatedCard)
        assertThat(dao.get(cardWithIssuer2.cardId)).isEqualTo(updatedCard)
    }

    @Test
    fun deleteCardTest() = runBlockingTest {
        addAllCards()
        dao.delete(cardWithIssuer2)
        val allCards = dao.getAll().first()
        assertThat(allCards).doesNotContain(cardWithIssuer2)
    }

    @Test
    fun getCardsLinkedToABankTest() = runBlockingTest {
        val card = cardWithIssuer3.copy(
            isLinkedToBank = true,
            bankAccountId = accountWithBank3.bankAccountId
        )
        dao.add(card)
        dao.add(card2WithIssuer3)
        val cardsLinkedToAccount = dao.getCardsLinkedToABank(accountWithBank3.bankAccountId).first()
        assertThat(cardsLinkedToAccount).containsExactly(card, card2WithIssuer3)
    }

    @Test
    fun getCardsWithIssuerDetailsTest() = runBlockingTest {
        addAllCards()
        val cards = dao.getCardsWithIssuerDetails().first()

        assertThat(cards[cardIssuer]).containsExactly(cardWithIssuer1, card2WithIssuer1)
        assertThat(cards[bankIssuesCards]).containsExactly(cardWithIssuer2, card2WithIssuer2)
        assertThat(cards[bankIssuesCardsHasCredentials]).containsExactly(cardWithIssuer3, card2WithIssuer3)
    }

    /*
    * Helper functions
    * */

    private suspend fun addIssuersAndAccounts() {
        val companyDao = database.companyDao
        companyDao.add(cardIssuer)
        companyDao.add(bankIssuesCards)
        companyDao.add(bankIssuesCardsHasCredentials)

        val accountDao = database.bankAccountDao
        accountDao.add(accountWithBank2)
        accountDao.add(accountWithBank3)
    }

    private suspend fun addAllCards() {
        dao.add(cardWithIssuer1)
        dao.add(card2WithIssuer1)
        dao.add(cardWithIssuer2)
        dao.add(card2WithIssuer2)
        dao.add(cardWithIssuer3)
        dao.add(card2WithIssuer3)
    }
}
