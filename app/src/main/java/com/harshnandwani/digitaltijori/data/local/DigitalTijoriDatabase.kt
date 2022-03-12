package com.harshnandwani.digitaltijori.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Card
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.domain.model.Credential

@Database(
    entities = [
        Company::class,
        BankAccount::class,
        Card::class,
        Credential::class
    ],
    version = 1
)
abstract class DigitalTijoriDatabase : RoomDatabase() {

    abstract val companyDao: CompanyDao

    abstract val bankAccountDao: BankAccountDao

    abstract val cardDao: CardDao

    abstract val credentialDao: CredentialDao

    companion object {
        const val DATABASE_NAME = "digital_tijori_db"
    }

}
