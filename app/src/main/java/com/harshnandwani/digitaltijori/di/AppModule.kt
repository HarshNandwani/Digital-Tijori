package com.harshnandwani.digitaltijori.di

import android.app.Application
import androidx.room.Room
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.data.local.DigitalTijoriDatabase
import com.harshnandwani.digitaltijori.data.repository.BankAccountRepositoryImpl
import com.harshnandwani.digitaltijori.data.repository.CardRepositoryImpl
import com.harshnandwani.digitaltijori.data.repository.CompanyRepositoryImpl
import com.harshnandwani.digitaltijori.data.repository.CredentialRepositoryImpl
import com.harshnandwani.digitaltijori.domain.repository.BankAccountRepository
import com.harshnandwani.digitaltijori.domain.repository.CardRepository
import com.harshnandwani.digitaltijori.domain.repository.CompanyRepository
import com.harshnandwani.digitaltijori.domain.repository.CredentialRepository
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.*
import com.harshnandwani.digitaltijori.domain.use_case.company.GetAllBanksUseCase
import com.harshnandwani.digitaltijori.domain.use_case.company.GetCompaniesHavingCredentialsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.credential.*
import com.harshnandwani.digitaltijori.presentation.util.UpdateCompaniesOnAppStart
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
    fun provideUpdateCompaniesOnAppStartMethod(
        app: Application,
        companyRepository: CompanyRepository
    ): UpdateCompaniesOnAppStart {
        return UpdateCompaniesOnAppStart(
            app.assets.open("companies.json").bufferedReader().use { it.readText() },
            app.resources.obtainTypedArray(R.array.company_icons),
            app.resources.obtainTypedArray(R.array.company_logos),
            companyRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetAllBanksUseCase(repository: CompanyRepository): GetAllBanksUseCase {
        return GetAllBanksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCompaniesHavingCredentialsUseCase(repository: CompanyRepository): GetCompaniesHavingCredentialsUseCase {
        return GetCompaniesHavingCredentialsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideBankAccountRepository(db: DigitalTijoriDatabase): BankAccountRepository {
        return BankAccountRepositoryImpl(db.bankAccountDao)
    }

    @Provides
    @Singleton
    fun provideAddBankAccountUseCase(repository: BankAccountRepository): AddBankAccountUseCase {
        return AddBankAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBankAccountUseCase(repository: BankAccountRepository): GetBankAccountUseCase {
        return GetBankAccountUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideUpdateBankAccountUseCase(repository: BankAccountRepository): UpdateBankAccountUseCase {
        return UpdateBankAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteBankAccountUseCase(repository: BankAccountRepository): DeleteBankAccountUseCase {
        return DeleteBankAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllAccountsWithBankDetailsUseCase(repository: BankAccountRepository): GetAllAccountsWithBankDetailsUseCase {
        return GetAllAccountsWithBankDetailsUseCase(repository)
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

    @Provides
    @Singleton
    fun provideAddCredentialUseCase(repository: CredentialRepository): AddCredentialUseCase {
        return AddCredentialUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCredentialUseCase(repository: CredentialRepository): GetCredentialUseCase {
        return GetCredentialUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateCredentialUseCase(repository: CredentialRepository): UpdateCredentialUseCase {
        return UpdateCredentialUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteCredentialUseCase(repository: CredentialRepository): DeleteCredentialUseCase {
        return DeleteCredentialUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllCredentialsWithEntityDetailsUseCase(repository: CredentialRepository): GetAllCredentialsWithEntityDetailsUseCase {
        return GetAllCredentialsWithEntityDetailsUseCase(repository)
    }

}
