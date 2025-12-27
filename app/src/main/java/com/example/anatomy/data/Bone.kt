package com.example.anatomy.data

import com.example.anatomy.ui.language.Language


// Data class representing a bone
data class Bone(
    val id: String,                        // Unique, language-independent ID
    val names: Map<Language, String>,      // Name per language
    val highlightDrawableRes: Int          // Drawable resource for highlighted bone
) {
    // Helper to get name for a specific language
    fun getName(language: Language): String = names[language] ?: names[Language.LATIN]!!
}
