package com.example.anatomy.data.settings

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.anatomy.ui.language.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class SettingsRepository(private val context: Context) {

    private val AUTO_NEXT = booleanPreferencesKey("auto_next")
    private val AUTO_NEXT_SECONDS = intPreferencesKey("auto_next_seconds")
    private val LANGUAGE = stringPreferencesKey("language")
    private val ANSWER_MODE = stringPreferencesKey("answer_mode")

    val settings: Flow<Settings> = context.dataStore.data.map { prefs ->
        Settings(
            autoNextEnabled = prefs[AUTO_NEXT] ?: true,
            autoNextSeconds = prefs[AUTO_NEXT_SECONDS] ?: 5,
            language = Language.valueOf(prefs[LANGUAGE] ?: Language.LATIN.name),
            answerMode = AnswerMode.valueOf(
                prefs[ANSWER_MODE] ?: AnswerMode.MULTIPLE_CHOICE.name
            )
        )
    }

    suspend fun updateSettings(update: Settings) {
        context.dataStore.edit { prefs ->
            prefs[AUTO_NEXT] = update.autoNextEnabled
            prefs[AUTO_NEXT_SECONDS] = update.autoNextSeconds
            prefs[LANGUAGE] = update.language.name
            prefs[ANSWER_MODE] = update.answerMode.name
        }
    }
}
