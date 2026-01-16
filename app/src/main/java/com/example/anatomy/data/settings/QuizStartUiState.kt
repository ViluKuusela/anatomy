package com.example.anatomy.data.settings

import com.example.anatomy.ui.language.Language

/**
 * A data class that represents the state for the quiz start screen.
 */
data class QuizStartUiState(
    val anatomyArea: String,
    val language: Language,
    val quizMode: QuizMode = QuizMode.CHOOSE
)
