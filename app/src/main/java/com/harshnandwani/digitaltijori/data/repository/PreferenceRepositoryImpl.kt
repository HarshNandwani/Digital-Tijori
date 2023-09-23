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

    override suspend fun setShowAboutApp(show: Boolean) {
        dataStore.setShowAboutApp(show)
    }

    override suspend fun getShouldShowAboutApp(): Boolean {
        return dataStore.getShowAboutApp()
    }

    override suspend fun isAppOpenedFirstTime(): Boolean {
        return dataStore.getOpenedFirstTime()
    }

    override suspend fun setAppOpened() {
        dataStore.setOpenedFirstTime()
    }

}
