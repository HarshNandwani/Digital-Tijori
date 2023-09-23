package com.harshnandwani.digitaltijori.core.di

import android.app.Application
import androidx.room.Room
import com.harshnandwani.digitaltijori.R
import com.harshnandwani.digitaltijori.data.local.DigitalTijoriDataStore
import com.harshnandwani.digitaltijori.data.local.DigitalTijoriDatabase
import com.harshnandwani.digitaltijori.data.repository.*
import com.harshnandwani.digitaltijori.domain.repository.*
import com.harshnandwani.digitaltijori.domain.use_case.auth.*
import com.harshnandwani.digitaltijori.domain.use_case.backup_restore.CreateBackupUseCase
import com.harshnandwani.digitaltijori.domain.use_case.backup_restore.IsEligibleForRestoreUseCase
import com.harshnandwani.digitaltijori.domain.use_case.backup_restore.EncryptDecryptDataUseCase
import com.harshnandwani.digitaltijori.domain.use_case.backup_restore.GetAllDataInJsonUseCase
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.*
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.AddBankAccountUseCase
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.GetAllAccountsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.*
import com.harshnandwani.digitaltijori.domain.use_case.company.GetAllBanksUseCase
import com.harshnandwani.digitaltijori.domain.use_case.company.GetAllCardIssuersUseCase
import com.harshnandwani.digitaltijori.domain.use_case.company.GetCompaniesHavingCredentialsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.credential.*
import com.harshnandwani.digitaltijori.domain.use_case.preference.SetAppOpenedUseCase
import com.harshnandwani.digitaltijori.domain.use_case.preference.SetDoNotShowAboutAppUseCase
import com.harshnandwani.digitaltijori.domain.use_case.preference.ShouldShowAboutAppUseCase
import com.harshnandwani.digitaltijori.presentation.util.UpdateCompaniesOnAppStart
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
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
    fun provideDigitalTijoriDataStore(app: Application): DigitalTijoriDataStore {
        return DigitalTijoriDataStore(app)
    }

    @Provides
    @Singleton
    fun providePreferenceRepository(dataStore: DigitalTijoriDataStore): PreferenceRepository {
        return PreferenceRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideShouldAuthenticateUseCase(repository: PreferenceRepository): ShouldAuthenticateUseCase {
        return ShouldAuthenticateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetAuthenticatedUseCase(repository: PreferenceRepository): SetAuthenticatedUseCase {
        return SetAuthenticatedUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideShouldShowAboutAppUseCase(repository: PreferenceRepository): ShouldShowAboutAppUseCase {
        return ShouldShowAboutAppUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetDoNotShowAboutAppUseCase(repository: PreferenceRepository): SetDoNotShowAboutAppUseCase {
        return SetDoNotShowAboutAppUseCase(repository)
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
    fun provideGetAllCardIssuersUseCase(repository: CompanyRepository): GetAllCardIssuersUseCase {
        return GetAllCardIssuersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCompaniesHavingCredentialsUseCase(repository: CompanyRepository): GetCompaniesHavingCredentialsUseCase {
        return GetCompaniesHavingCredentialsUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideValidateBankAccountUseCase(): ValidateBankAccountUseCase {
        return ValidateBankAccountUseCase()
    }

    @Provides
    @Singleton
    fun provideBankAccountRepository(db: DigitalTijoriDatabase): BankAccountRepository {
        return BankAccountRepositoryImpl(db.bankAccountDao, provideCompanyRepository(db))
    }

    @Provides
    @Singleton
    fun provideAddBankAccountUseCase(repository: BankAccountRepository): AddBankAccountUseCase {
        return AddBankAccountUseCase(repository, provideValidateBankAccountUseCase())
    }

    @Provides
    @Singleton
    fun provideGetBankAccountUseCase(repository: BankAccountRepository): GetBankAccountUseCase {
        return GetBankAccountUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideUpdateBankAccountUseCase(repository: BankAccountRepository): UpdateBankAccountUseCase {
        return UpdateBankAccountUseCase(repository, provideValidateBankAccountUseCase())
    }

    @Provides
    @Singleton
    fun provideDeleteBankAccountUseCase(repository: BankAccountRepository): DeleteBankAccountUseCase {
        return DeleteBankAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllAccountsUseCase(repository: BankAccountRepository): GetAllAccountsUseCase {
        return GetAllAccountsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCardRepository(db: DigitalTijoriDatabase): CardRepository {
        return CardRepositoryImpl(
            db.cardDao,
            provideCompanyRepository(db),
            provideBankAccountRepository(db)
        )
    }

    @Provides
    @Singleton
    fun provideGetCardNumberLengthUseCase(): GetCardNumberLengthUseCase {
        return GetCardNumberLengthUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateCardUseCase(): ValidateCardUseCase {
        return ValidateCardUseCase(provideGetCardNumberLengthUseCase())
    }

    @Provides
    @Singleton
    fun provideAddCardUseCase(repository: CardRepository): AddCardUseCase {
        return AddCardUseCase(repository, provideValidateCardUseCase())
    }

    @Provides
    @Singleton
    fun provideGetCardUseCase(repository: CardRepository): GetCardUseCase {
        return GetCardUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateCardUseCase(repository: CardRepository): UpdateCardUseCase {
        return UpdateCardUseCase(repository, provideValidateCardUseCase())
    }

    @Provides
    @Singleton
    fun provideDeleteCardUseCase(repository: CardRepository): DeleteCardUseCase {
        return DeleteCardUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllCardsUseCase(repository: CardRepository): GetAllCardsUseCase {
        return GetAllCardsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCardsLinkedToAccountUseCase(repository: CardRepository): GetCardsLinkedToAccountUseCase {
        return GetCardsLinkedToAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCredentialRepository(db: DigitalTijoriDatabase): CredentialRepository {
        return CredentialRepositoryImpl(
            db.credentialDao,
            provideCompanyRepository(db),
            provideBankAccountRepository(db)
        )
    }

    @Provides
    @Singleton
    fun provideValidateCredentialUseCase(): ValidateCredentialUseCase {
        return ValidateCredentialUseCase()
    }

    @Provides
    @Singleton
    fun provideAddCredentialUseCase(repository: CredentialRepository): AddCredentialUseCase {
        return AddCredentialUseCase(repository, provideValidateCredentialUseCase())
    }

    @Provides
    @Singleton
    fun provideGetCredentialUseCase(repository: CredentialRepository): GetCredentialUseCase {
        return GetCredentialUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateCredentialUseCase(repository: CredentialRepository): UpdateCredentialUseCase {
        return UpdateCredentialUseCase(repository, provideValidateCredentialUseCase())
    }

    @Provides
    @Singleton
    fun provideDeleteCredentialUseCase(repository: CredentialRepository): DeleteCredentialUseCase {
        return DeleteCredentialUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllCredentialsUseCase(repository: CredentialRepository): GetAllCredentialsUseCase {
        return GetAllCredentialsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCredentialsLinkedToAccountUseCase(repository: CredentialRepository): GetCredentialsLinkedToAccountUseCase {
        return GetCredentialsLinkedToAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideBackupRepository(app: Application): BackupRepository {
        return BackupRepositoryImpl(app.filesDir)
    }

    @Provides
    @Singleton
    fun provideGetAllDataInJsonUseCase(
        getAllAccountsUseCase: GetAllAccountsUseCase,
        getAllCardsUseCase: GetAllCardsUseCase,
        getAllCredentialsUseCase: GetAllCredentialsUseCase
    ): GetAllDataInJsonUseCase {
        return GetAllDataInJsonUseCase(
            getAllAccountsUseCase,
            getAllCardsUseCase,
            getAllCredentialsUseCase
        )
    }

    @Provides
    @Singleton
    fun provideEncryptDecryptDataUseCase(): EncryptDecryptDataUseCase {
        return EncryptDecryptDataUseCase()
    }

    @Provides
    @Singleton
    fun provideCreateBackupUseCase(
        getAllDataInJsonUseCase: GetAllDataInJsonUseCase,
        encryptDecryptDataUseCase: EncryptDecryptDataUseCase,
        repository: BackupRepository
    ): CreateBackupUseCase {
        return CreateBackupUseCase(getAllDataInJsonUseCase, encryptDecryptDataUseCase, repository)
    }

    @Provides
    @Singleton
    fun provideEligibleForRestoreUseCase(
        preferenceRepository: PreferenceRepository,
        bankAccountRepository: BankAccountRepository,
        cardRepository: CardRepository,
        credentialRepository: CredentialRepository
    ): IsEligibleForRestoreUseCase {
        return IsEligibleForRestoreUseCase(
            preferenceRepository,
            bankAccountRepository,
            cardRepository,
            credentialRepository
        )
    }

    @Provides
    @Singleton
    fun provideSetAppOpenedUseCase(repository: PreferenceRepository): SetAppOpenedUseCase {
        return SetAppOpenedUseCase(repository)
    }
}
