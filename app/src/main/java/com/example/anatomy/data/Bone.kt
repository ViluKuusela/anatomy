package com.example.anatomy.data

import androidx.annotation.DrawableRes
import com.example.anatomy.ui.language.Language

/**
 * Represents a single bone in the anatomy quiz.
 *
 * @param id A unique identifier for the bone.
 * @param names A map of languages to the bone's name in that language.
 * @param baseDrawableRes The resource ID for the base image of the anatomical area (e.g., the full hand).
 * @param highlightMaskRes The resource ID for the drawable that masks just this specific bone, used for highlighting.
 */
data class Bone(
    val id: String,
    val names: Map<Language, String>,
    @DrawableRes val baseDrawableRes: Int,
    @DrawableRes val highlightMaskRes: Int
) {
    fun getName(language: Language): String {
        return names[language] ?: names[Language.LATIN] ?: id
    }
}
