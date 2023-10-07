package com.harshnandwani.digitaltijori.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.harshnandwani.digitaltijori.data.local.DigitalTijoriDatabase
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bank
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCards
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.bankIssuesCardsHasCredentials
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.cardIssuer
import com.harshnandwani.digitaltijori.data.util.DummyCompanies.entity
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
class CompanyDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: DigitalTijoriDatabase
    private lateinit var dao: CompanyDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DigitalTijoriDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.companyDao
    }

    @After
    fun closeDown() {
        database.close()
    }

    @Test
    fun addAndGetAllCompaniesTest() = runTest {
        addDummyData()
        val allCompanies = dao.getAll()
        assertThat(allCompanies).containsExactly(
            bank,
            bankIssuesCards,
            bankIssuesCardsHasCredentials,
            cardIssuer,
            entity
        )
    }

    @Test
    fun getAllBanksTest() = runTest {
        addDummyData()
        val allBanks = dao.getAllBanks().first()
        assertThat(allBanks).containsExactly(
            bank,
            bankIssuesCards,
            bankIssuesCardsHasCredentials
        )
    }

    @Test
    fun getAllCardIssuersTest() = runTest {
        addDummyData()
        val allIssuers = dao.getAllCardIssuers().first()
        assertThat(allIssuers).containsExactly(
            bankIssuesCards,
            bankIssuesCardsHasCredentials,
            cardIssuer
        )
    }

    @Test
    fun getAllCompaniesWithCredentialsTest() = runTest {
        addDummyData()
        val allCompaniesWithCredentials = dao.getAllCompaniesWithCredentials().first()
        assertThat(allCompaniesWithCredentials).containsExactly(
            bankIssuesCardsHasCredentials,
            entity
        )
    }

    @Test
    fun updateCompanyTest() = runTest {
        dao.add(bank)
        val updatedCompany = bank.copy(name = "Updated Bank Name!")
        dao.update(updatedCompany)
        assertThat(dao.getAll()).contains(updatedCompany)
    }

    private suspend fun addDummyData() {
        dao.add(bank)
        dao.add(bankIssuesCards)
        dao.add(bankIssuesCardsHasCredentials)
        dao.add(cardIssuer)
        dao.add(entity)
    }

}
