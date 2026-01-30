package com.example.anatomy.data

import androidx.annotation.DrawableRes
import com.example.anatomy.ui.language.Language

/**
 * Represents a single bone in the anatomy quiz.
 *
 * @param id A unique identifier for the bone.
 * @param names A map of languages to the bone's name in that language. 
 *              Multiple names can be separated by a pipe character '|' (e.g., "Lapaluu | Lapaluut").
 * @param baseDrawableRes The resource ID for the base image of the anatomical area.
 * @param highlightMaskRes The resource ID for the drawable that masks just this specific bone.
 */
data class Bone(
    val id: String,
    val names: Map<Language, String>,
    @DrawableRes val baseDrawableRes: Int,
    @DrawableRes val highlightMaskRes: Int
) {
    /**
     * Returns the primary name for display.
     */
    fun getName(language: Language): String {
        return names[language]?.split("|")?.first()?.trim() ?: names[Language.LATIN] ?: id
    }

    /**
     * Returns a list of all acceptable names for this bone in the given language.
     */
    fun getAllNames(language: Language): List<String> {
        val nameString = names[language] ?: return emptyList()
        return nameString.split("|").map { it.trim() }
    }
}
