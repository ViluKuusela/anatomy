package com.example.anatomy.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.anatomy.ui.language.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Centralized DataStore for all app settings.
private val Context.dataStore by preferencesDataStore(name = "settings")

/**
 * A repository for managing all application settings, both for the quiz and general UI.
 * This class centralizes DataStore access.
 */
class SettingsRepository(private val context: Context) {

    // Keys for DataStore.
    private object PreferencesKeys {
        // General settings
        val ENABLE_AUTO_ADVANCE = booleanPreferencesKey("enable_auto_advance")
        val AUTO_NEXT_DELAY = intPreferencesKey("auto_next_delay")

        // Quiz start settings
        val ANATOMY_AREA = stringPreferencesKey("anatomy_area")
        val LANGUAGE = stringPreferencesKey("language")
        val IS_MULTIPLE_CHOICE = booleanPreferencesKey("is_multiple_choice")
    }

    // Flow for general app settings.
    val settingsFlow: Flow<SettingsData> = context.dataStore.data.map { prefs ->
        SettingsData(
            enableAutoAdvance = prefs[PreferencesKeys.ENABLE_AUTO_ADVANCE] ?: true,
            autoNextDelaySeconds = prefs[PreferencesKeys.AUTO_NEXT_DELAY] ?: 3
        )
    }

    // Flow for quiz start settings.
    val quizStartUiState: Flow<QuizStartUiState> = context.dataStore.data.map { prefs ->
        QuizStartUiState(
            anatomyArea = prefs[PreferencesKeys.ANATOMY_AREA] ?: "Hand",
            language = Language.valueOf(prefs[PreferencesKeys.LANGUAGE] ?: Language.LATIN.name),
            isMultipleChoice = prefs[PreferencesKeys.IS_MULTIPLE_CHOICE] ?: true
        )
    }

    // --- Setters for general settings ---

    suspend fun setAutoAdvanceEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.ENABLE_AUTO_ADVANCE] = enabled
        }
    }

    suspend fun setAutoNextDelay(seconds: Int) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.AUTO_NEXT_DELAY] = seconds
        }
    }

    // --- Setters for quiz start settings ---

    suspend fun setAnatomyArea(anatomyArea: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.ANATOMY_AREA] = anatomyArea
        }
    }

    suspend fun setLanguage(language: Language) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.LANGUAGE] = language.name
        }
    }

    suspend fun setIsMultipleChoice(isMultipleChoice: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.IS_MULTIPLE_CHOICE] = isMultipleChoice
        }
    }
}
