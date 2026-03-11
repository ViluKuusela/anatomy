package com.example.anatomy.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anatomy.data.settings.SettingsRepository
import com.example.anatomy.ui.language.Language
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = repository.settingsFlow.map {
        SettingsUiState(it.enableAutoAdvance, it.autoNextDelaySeconds, it.uiLanguage)
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
}
