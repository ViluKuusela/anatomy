package com.viluappdev.anatomy.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viluappdev.anatomy.data.settings.SettingsRepository
import com.viluappdev.anatomy.ui.language.Language
import com.viluappdev.anatomy.ui.theme.AppThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = repository.settingsFlow.map {
        SettingsUiState(
            enableAutoAdvance = it.enableAutoAdvance,
            autoNextDelaySeconds = it.autoNextDelaySeconds,
            uiLanguage = it.uiLanguage,
            themeMode = it.themeMode
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingsUiState()
    )

    fun setAutoAdvanceEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setAutoAdvanceEnabled(enabled)
        }
    }

    fun setAutoNextDelay(seconds: Int) {
        viewModelScope.launch {
            repository.setAutoNextDelay(seconds)
        }
    }

    fun setUiLanguage(language: Language) {
        viewModelScope.launch {
            repository.setUiLanguage(language)
        }
    }

    fun setThemeMode(mode: AppThemeMode) {
        viewModelScope.launch {
            repository.setThemeMode(mode)
        }
    }
}
