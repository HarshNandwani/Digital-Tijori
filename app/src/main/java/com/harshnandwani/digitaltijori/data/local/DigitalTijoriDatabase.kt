package com.harshnandwani.digitaltijori.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harshnandwani.digitaltijori.data.local.dao.BankAccountDao
import com.harshnandwani.digitaltijori.data.local.dao.CardDao
import com.harshnandwani.digitaltijori.data.local.dao.CompanyDao
import com.harshnandwani.digitaltijori.data.local.dao.CredentialDao
import com.harshnandwani.digitaltijori.data.local.entity.BankAccountEntity
import com.harshnandwani.digitaltijori.data.local.entity.CardEntity
import com.harshnandwani.digitaltijori.data.local.entity.CompanyEntity
import com.harshnandwani.digitaltijori.data.local.entity.CredentialEntity

@Database(
    entities = [
        CompanyEntity::class,
        BankAccountEntity::class,
        CardEntity::class,
        CredentialEntity::class
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
