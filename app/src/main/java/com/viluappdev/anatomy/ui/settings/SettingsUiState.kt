package com.viluappdev.anatomy.ui.settings

import com.viluappdev.anatomy.ui.language.Language
import com.viluappdev.anatomy.ui.theme.AppThemeMode

/**
 * A data class that represents the state of the settings screen.
 */
data class SettingsUiState(
    val enableAutoAdvance: Boolean = true,
    val autoNextDelaySeconds: Int = 3,
    val uiLanguage: Language = Language.ENGLISH,
    val themeMode: AppThemeMode = AppThemeMode.FOLLOW_SYSTEM
)
