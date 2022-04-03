package com.harshnandwani.digitaltijori.data.repository

import com.harshnandwani.digitaltijori.data.local.DigitalTijoriDataStore
import com.harshnandwani.digitaltijori.domain.repository.PreferenceRepository

class PreferenceRepositoryImpl(private val dataStore: DigitalTijoriDataStore) : PreferenceRepository {

    override suspend fun setAuthenticatedTimestamp(time: Long) {
        dataStore.setAuthenticatedTimestamp(time)
    }

    override suspend fun getLastAuthenticatedTimestamp(): Long {
        return dataStore.getLastAuthenticatedTimestamp()
    }

}
