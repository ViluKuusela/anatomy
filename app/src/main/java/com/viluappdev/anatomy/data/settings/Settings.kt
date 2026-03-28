package com.viluappdev.anatomy.data.settings

import com.viluappdev.anatomy.ui.language.Language

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
