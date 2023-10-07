package com.harshnandwani.digitaltijori.data.local.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.harshnandwani.digitaltijori.data.local.DigitalTijoriDatabase
import com.harshnandwani.digitaltijori.data.util.DummyAccounts.accountWithBank3
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCardsHasCredentials
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.entity
import com.harshnandwani.digitaltijori.data.util.DummyCredentials.creds2WithEntity1
import com.harshnandwani.digitaltijori.data.util.DummyCredentials.creds2WithEntity2
import com.harshnandwani.digitaltijori.data.util.DummyCredentials.creds3WithEntity2
import com.harshnandwani.digitaltijori.data.util.DummyCredentials.credsWithEntity1
import com.harshnandwani.digitaltijori.data.util.DummyCredentials.credsWithEntity2
import com.harshnandwani.digitaltijori.data.util.DummyCredentials.credsWithInvalidAccount
import com.harshnandwani.digitaltijori.data.util.DummyCredentials.credsWithInvalidCompany
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CredentialDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DigitalTijoriDatabase
    private lateinit var dao: CredentialDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DigitalTijoriDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.credentialDao
        runTest {
            addEntitiesAndAccounts()
        }
    }

    @After
    fun closeDown() {
        database.close()
    }

    /*
    * Test to check the companyId foreign key constraint
    * */
    @Test
    fun addCredentialWithInvalidEntity() = runTest {
        try {
            dao.add(credsWithInvalidCompany)
        } catch (e: SQLiteConstraintException) {
            assertThat(e.message).contains("FOREIGN KEY constraint failed")
        }
        val allCreds = dao.getAll().first()
        assertThat(allCreds).doesNotContain(credsWithInvalidCompany)
    }

    /*
    * Test to check the bankAccountId foreign key constraint
    * */
    @Test
    fun addCredentialWithInvalidAccount() = runTest {
        try {
            dao.add(credsWithInvalidAccount)
        } catch (e: SQLiteConstraintException) {
            assertThat(e.message).contains("FOREIGN KEY constraint failed")
        }
        val allCreds = dao.getAll().first()
        assertThat(allCreds).doesNotContain(credsWithInvalidAccount)
    }

    @Test
    fun addAndGetCredentialsTest() = runTest {
        dao.add(credsWithEntity1)
        dao.add(creds2WithEntity1)
        val allCreds = dao.getAll().first()
        assertThat(allCreds).containsExactly(credsWithEntity1, creds2WithEntity1)
    }

    @Test
    fun addCredentialsFromDifferentEntities() = runTest {
        addAllCredentials()
        val allCreds = dao.getAll().first()
        assertThat(allCreds).containsExactly(
            credsWithEntity1,
            creds2WithEntity1,
            credsWithEntity2,
            creds2WithEntity2,
            creds3WithEntity2
        )
    }

    @Test
    fun getCredentialTest() = runTest {
        dao.add(credsWithEntity1)
        assertThat(dao.get(credsWithEntity1.credentialId)).isEqualTo(credsWithEntity1)
    }

    @Test
    fun updateCredentialTest() = runTest {
        dao.add(credsWithEntity2)
        val updatedCredentials = credsWithEntity2.copy(
            isLinkedToBank = true,
            bankAccountId = accountWithBank3.bankAccountId,
            username = "updated_username",
            password = "updated_password",
            alias = "updated_alias"
        )
        dao.update(updatedCredentials)
        assertThat(dao.get(credsWithEntity2.credentialId)).isEqualTo(updatedCredentials)
    }

    @Test
    fun deleteCredentialTest() = runTest {
        addAllCredentials()
        dao.delete(credsWithEntity2)
        val allCreds = dao.getAll().first()
        assertThat(allCreds).doesNotContain(credsWithEntity2)
    }

    @Test
    fun getCredentialsLinkedToAccountTest() = runTest {
        addAllCredentials()
        val creds = dao.getCredentialsLinkedToAccount(accountWithBank3.bankAccountId).first()
        assertThat(creds).containsExactly(creds2WithEntity2, creds3WithEntity2)
    }

    @Test
    fun getAllCredentialsWithEntityDetailsTest() = runTest {
        addAllCredentials()
        val creds = dao.getAllCredentialsWithEntityDetails().first()
        assertThat(creds[entity]).containsExactly(credsWithEntity1, creds2WithEntity1)
        assertThat(creds[bankIssuesCardsHasCredentials]).containsExactly(credsWithEntity2, creds2WithEntity2, creds3WithEntity2)
    }

    /*
    * Helper functions
    * */

    private suspend fun addEntitiesAndAccounts() {
        val companyDao = database.companyDao
        companyDao.add(entity)
        companyDao.add(bankIssuesCardsHasCredentials)

        val accountDao = database.bankAccountDao
        accountDao.add(accountWithBank3)
    }

    private suspend fun addAllCredentials() {
        dao.add(credsWithEntity1)
        dao.add(creds2WithEntity1)
        dao.add(credsWithEntity2)
        dao.add(creds2WithEntity2)
        dao.add(creds3WithEntity2)
    }
}
