package com.example.anatomy.data

import androidx.annotation.DrawableRes

data class Bone(
    val id: String,
    val names: Map<Language, String>,
    @DrawableRes val drawableRes: Int
)

enum class Language {
    LATIN, ENGLISH, FINNISH
}
