package com.harshnandwani.digitaltijori.di

import android.app.Application
import android.content.ContentValues
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.harshnandwani.digitaltijori.data.local.DigitalTijoriDatabase
import com.harshnandwani.digitaltijori.data.repository.BankAccountRepositoryImpl
import com.harshnandwani.digitaltijori.data.repository.CardRepositoryImpl
import com.harshnandwani.digitaltijori.data.repository.CompanyRepositoryImpl
import com.harshnandwani.digitaltijori.data.repository.CredentialRepositoryImpl
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository
import com.harshnandwani.digitaltijori.domain.use_case.company.ConvertJsonToCompaniesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDigitalTijoriDatabase(app: Application): DigitalTijoriDatabase {
        return Room.databaseBuilder(
            app,
            DigitalTijoriDatabase::class.java,
            DigitalTijoriDatabase.DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val companiesJson = app.assets.open("companies.json")
                        .bufferedReader().use { it.readText() }

                Executors.newSingleThreadExecutor().execute {

                    val convertJsonToCompanies = ConvertJsonToCompaniesUseCase()
                    convertJsonToCompanies(companiesJson).forEach { company ->
                        val contentValues = ContentValues()
                        contentValues.put("name", company.name)
                        contentValues.put("isABank", company.isABank)
                        contentValues.put("issuesCards", company.issuesCards)
                        contentValues.put("hasCredentials", company.hasCredentials)
                        try {
                            db.insert("Company", SQLiteDatabase.CONFLICT_REPLACE, contentValues)
                        }catch (e: SQLException){
                            Log.d("Database.onCreate Callback", "Exception while inserting company: $e")
                        }
                    }

                }
            }
        }).build()
    }

    @Provides
    @Singleton
    fun provideCompanyRepository(db: DigitalTijoriDatabase): CompanyRepository {
        return CompanyRepositoryImpl(db.companyDao)
    }

    @Provides
    @Singleton
    fun provideBankAccountRepository(db: DigitalTijoriDatabase): BankAccountRepository {
        return BankAccountRepositoryImpl(db.bankAccountDao)
    }

    @Provides
    @Singleton
    fun provideCardRepository(db: DigitalTijoriDatabase): CardRepository {
        return CardRepositoryImpl(db.cardDao)
    }

    @Provides
    @Singleton
    fun provideCredentialRepository(db: DigitalTijoriDatabase): CredentialRepository {
        return CredentialRepositoryImpl(db.credentialDao)
    }

}
