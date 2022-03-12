package com.harshnandwani.digitaltijori.di

import android.app.Application
import androidx.room.Room
import com.harshnandwani.digitaltijori.data.local.DigitalTijoriDatabase
import com.harshnandwani.digitaltijori.data.repository.BankAccountRepositoryImpl
import com.harshnandwani.digitaltijori.data.repository.CardRepositoryImpl
import com.harshnandwani.digitaltijori.data.repository.CompanyRepositoryImpl
import com.harshnandwani.digitaltijori.data.repository.CredentialRepositoryImpl
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
        ).build()
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
