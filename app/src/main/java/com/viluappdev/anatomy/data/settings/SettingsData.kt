package com.viluappdev.anatomy.data.settings

import com.viluappdev.anatomy.ui.language.Language
import com.viluappdev.anatomy.ui.theme.AppThemeMode

/**
 * A data class that represents the general app settings data.
 */
data class SettingsData(
    val enableAutoAdvance: Boolean,
    val autoNextDelaySeconds: Int,
    val uiLanguage: Language,
    val themeMode: AppThemeMode
)
