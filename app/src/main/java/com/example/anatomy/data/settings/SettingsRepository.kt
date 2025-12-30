package com.example.anatomy.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context) {

    private val ENABLE_AUTO_ADVANCE = booleanPreferencesKey("enable_auto_advance")
    private val AUTO_NEXT_DELAY = intPreferencesKey("auto_next_delay")

    val settingsFlow: Flow<SettingsData> = context.dataStore.data.map { prefs ->
        SettingsData(
            enableAutoAdvance = prefs[ENABLE_AUTO_ADVANCE] ?: true,
            autoNextDelaySeconds = prefs[AUTO_NEXT_DELAY] ?: 3
        )
    }

    suspend fun setAutoAdvanceEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[ENABLE_AUTO_ADVANCE] = enabled
        }
    }

    suspend fun setAutoNextDelay(seconds: Int) {
        context.dataStore.edit { prefs ->
            prefs[AUTO_NEXT_DELAY] = seconds
        }
    }
}
