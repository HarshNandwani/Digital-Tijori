package com.harshnandwani.digitaltijori.data.local

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.account
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.accountWithBank1
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.account2WithBank1
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.accountWithBank2
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.account2WithBank2
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.account2WithBank3
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.accountWithBank3
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bank
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCards
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCardsHasCredentials
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
class BankAccountDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DigitalTijoriDatabase
    private lateinit var dao: BankAccountDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DigitalTijoriDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.bankAccountDao
        runBlockingTest {
            addBanks()
        }
    }

    @After
    fun closeDown() {
        database.close()
    }

    /*
    * Test to check the foreign key constraint of BankAccount
    * */
    @Test
    fun addAccountWithoutBankTest() = runBlockingTest {
        try {
            dao.add(account)
        } catch (e: SQLiteConstraintException) {
            assertThat(e.message).contains("FOREIGN KEY constraint failed")
        }

        val allAccounts = dao.getAll().first()
        assertThat(allAccounts).doesNotContain(accountWithBank1)
    }

    @Test
    fun addAndGetAllAccountTest() = runBlockingTest {
        dao.add(accountWithBank1)
        dao.add(account2WithBank1)
        val allAccounts = dao.getAll().first()
        assertThat(allAccounts).containsExactly(accountWithBank1, account2WithBank1)
    }

    @Test
    fun addAccountsFromDifferentBanksTest() = runBlockingTest {
        addAllAccounts()
        val allAccounts = dao.getAll().first()
        assertThat(allAccounts).containsExactly(
            accountWithBank1,
            account2WithBank1,
            accountWithBank2,
            account2WithBank2,
            accountWithBank3,
            account2WithBank3
        )
    }

    @Test
    fun getBankAccountTest() = runBlockingTest {
        dao.add(accountWithBank1)
        assertThat(dao.get(accountWithBank1.bankAccountId)).isEqualTo(accountWithBank1)
    }

    @Test
    fun updateBankAccountTest() = runBlockingTest {
        dao.add(accountWithBank1)
        val updatedAccount = accountWithBank1.copy(
            holderName = "Updated name",
            accountNumber = "4823914912489",
            ifsc = "updated_ifsc",
            phoneNumber = "123456",
            alias = "updated_alias"
        )
        dao.update(updatedAccount)
        assertThat(dao.get(accountWithBank1.bankAccountId)).isEqualTo(updatedAccount)
    }

    @Test
    fun deleteBankAccountTest() = runBlockingTest {
        addAllAccounts()
        dao.delete(accountWithBank2)
        val allAccounts = dao.getAll().first()
        assertThat(allAccounts).doesNotContain(accountWithBank2)
    }

    @Test
    fun getAccountsWithBankDetailsTest() = runBlockingTest {
        addAllAccounts()
        val accounts = dao.getAccountsWithBankDetails().first()

        assertThat(accounts[bank]).containsExactly(accountWithBank1, account2WithBank1)
        assertThat(accounts[bankIssuesCards]).containsExactly(accountWithBank2, account2WithBank2)
        assertThat(accounts[bankIssuesCardsHasCredentials]).containsExactly(accountWithBank3, account2WithBank3)
    }

    /*
    * Helper functions
    * */

    private suspend fun addBanks() {
        val companyDao = database.companyDao
        companyDao.add(bank)
        companyDao.add(bankIssuesCards)
        companyDao.add(bankIssuesCardsHasCredentials)
    }

    private suspend fun addAllAccounts() {
        dao.add(accountWithBank1)
        dao.add(account2WithBank1)
        dao.add(accountWithBank2)
        dao.add(account2WithBank2)
        dao.add(accountWithBank3)
        dao.add(account2WithBank3)
    }

}
