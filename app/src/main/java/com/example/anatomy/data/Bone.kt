package com.example.anatomy.data

import androidx.annotation.DrawableRes

// Supported languages for bone names
enum class Language {
    LATIN, ENGLISH, FINNISH
}

// Data class representing a bone
data class Bone(
    val id: String,                        // Unique, language-independent ID
    val names: Map<Language, String>,      // Name per language
    @DrawableRes val drawableRes: Int      // Drawable resource for highlighted bone
) {
    // Helper to get name for a specific language
    fun getName(language: Language): String = names[language] ?: names[Language.LATIN]!!
}
