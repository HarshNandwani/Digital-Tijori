package com.harshnandwani.digitaltijori.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

class DigitalTijoriDataStore(private val context: Context) {

    companion object {

        private val Context.dataStore by preferencesDataStore(
            name = "DigitalTijoriDataStore"
        )

        private val LAST_AUTHENTICATED = longPreferencesKey("last_authenticated")
        private val SHOW_ABOUT_APP = booleanPreferencesKey("show_about_app")
        private val OPENED_FIRST_TIME = booleanPreferencesKey("opened_first_time")
    }

    suspend fun setAuthenticatedTimestamp(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[LAST_AUTHENTICATED] = time
        }
    }

    suspend fun getLastAuthenticatedTimestamp(): Long =
        context.dataStore.data.firstOrNull()?.get(LAST_AUTHENTICATED) ?: -1L

    suspend fun setShowAboutApp(show: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_ABOUT_APP] = show
        }
    }

    suspend fun getShowAboutApp(): Boolean =
        context.dataStore.data.firstOrNull()?.get(SHOW_ABOUT_APP) ?: true

    suspend fun setOpenedFirstTime() {
        context.dataStore.edit { preferences ->
            preferences[OPENED_FIRST_TIME] = false
        }
    }

    suspend fun getOpenedFirstTime(): Boolean = context.dataStore.data.firstOrNull()?.get(OPENED_FIRST_TIME) ?: true
}
