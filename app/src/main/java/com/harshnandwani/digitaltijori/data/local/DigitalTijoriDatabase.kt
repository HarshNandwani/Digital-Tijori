package com.harshnandwani.digitaltijori.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harshnandwani.digitaltijori.data.local.dao.BankAccountDao
import com.harshnandwani.digitaltijori.data.local.dao.CardDao
import com.harshnandwani.digitaltijori.data.local.dao.CompanyDao
import com.harshnandwani.digitaltijori.data.local.dao.CredentialDao
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
@TypeConverters(com.harshnandwani.digitaltijori.data.local.TypeConverters::class)
abstract class DigitalTijoriDatabase : RoomDatabase() {

    abstract val companyDao: CompanyDao

    abstract val bankAccountDao: BankAccountDao

    abstract val cardDao: CardDao

    abstract val credentialDao: CredentialDao

    companion object {
        const val DATABASE_NAME = "digital_tijori_db"
    }

}
