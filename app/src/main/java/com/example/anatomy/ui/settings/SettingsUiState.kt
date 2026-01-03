package com.example.anatomy.ui.settings

/**
 * A data class that represents the state of the settings screen.
 */
data class SettingsUiState(
    val enableAutoAdvance: Boolean = true,
    val autoNextDelaySeconds: Int = 3
)
