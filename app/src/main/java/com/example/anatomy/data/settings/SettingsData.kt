package com.example.anatomy.data.settings

import com.example.anatomy.ui.language.Language

/**
 * A data class that represents the general app settings data.
 */
data class SettingsData(
    val enableAutoAdvance: Boolean,
    val autoNextDelaySeconds: Int,
    val uiLanguage: Language // UI language preference (English or Finnish)
)
