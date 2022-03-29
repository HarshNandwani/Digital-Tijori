package com.harshnandwani.digitaltijori.presentation.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

class DigitalTijoriDataStore(private val context: Context) {

    companion object {

        private val Context.dataStore by preferencesDataStore(
            name = "DigitalTijoriDataStore"
        )

        private val LAST_AUTHENTICATED = longPreferencesKey("last_authenticated");

    }

    suspend fun setAuthenticatedTimestamp(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[LAST_AUTHENTICATED] = time
        }
    }

    suspend fun getLastAuthenticatedTimestamp(): Long =
        context.dataStore.data.firstOrNull()?.get(LAST_AUTHENTICATED) ?: -1L

}
