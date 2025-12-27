package com.example.anatomy.data.settings

import com.example.anatomy.ui.language.Language

data class Settings(
    val autoNextEnabled: Boolean = true,
    val autoNextSeconds: Int = 5,
    val language: Language = Language.LATIN,
    val answerMode: AnswerMode = AnswerMode.MULTIPLE_CHOICE
)

enum class AnswerMode {
    MULTIPLE_CHOICE,
    TEXT_INPUT
}
