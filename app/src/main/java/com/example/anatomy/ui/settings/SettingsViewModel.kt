package com.example.anatomy.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anatomy.data.settings.SettingsData
import com.example.anatomy.data.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.settingsFlow.collect { settings: SettingsData ->
                _uiState.value = SettingsUiState(
                    enableAutoAdvance = settings.enableAutoAdvance,
                    autoNextDelaySeconds = settings.autoNextDelaySeconds
                )
            }
        }
    }

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
}
