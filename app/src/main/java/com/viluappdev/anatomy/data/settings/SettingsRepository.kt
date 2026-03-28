package com.viluappdev.anatomy.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.viluappdev.anatomy.ui.language.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

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
        val UI_LANGUAGE = stringPreferencesKey("ui_language")

        // Quiz start settings
        val ANATOMY_AREA = stringPreferencesKey("anatomy_area")
        val LANGUAGE = stringPreferencesKey("language")
        val QUIZ_MODE = stringPreferencesKey("quiz_mode")
    }

    // Flow for general app settings.
    val settingsFlow: Flow<SettingsData> = context.dataStore.data.map { prefs ->
        // Use system language as default if no preference is saved
        val systemLanguage = if (Locale.getDefault().language == "fi") Language.FINNISH else Language.ENGLISH
        
        SettingsData(
            enableAutoAdvance = prefs[PreferencesKeys.ENABLE_AUTO_ADVANCE] ?: true,
            autoNextDelaySeconds = prefs[PreferencesKeys.AUTO_NEXT_DELAY] ?: 3,
            uiLanguage = Language.valueOf(prefs[PreferencesKeys.UI_LANGUAGE] ?: systemLanguage.name)
        )
    }

    // Flow for quiz start settings.
    val quizStartUiState: Flow<QuizStartUiState> = context.dataStore.data.map { prefs ->
        QuizStartUiState(
            anatomyArea = prefs[PreferencesKeys.ANATOMY_AREA] ?: "Hand",
            language = Language.valueOf(prefs[PreferencesKeys.LANGUAGE] ?: Language.LATIN.name),
            quizMode = QuizMode.valueOf(prefs[PreferencesKeys.QUIZ_MODE] ?: QuizMode.CHOOSE.name)
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

    suspend fun setUiLanguage(language: Language) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.UI_LANGUAGE] = language.name
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

    suspend fun setQuizMode(mode: QuizMode) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.QUIZ_MODE] = mode.name
        }
    }
}
