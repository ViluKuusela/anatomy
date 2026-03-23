package com.example.anatomy.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anatomy.data.settings.QuizMode
import com.example.anatomy.data.settings.QuizStartUiState
import com.example.anatomy.data.settings.SettingsRepository
import com.example.anatomy.ui.language.Language
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuizStartViewModel(
    private val repository: SettingsRepository,
    initialState: QuizStartUiState
) : ViewModel() {

    val uiState: StateFlow<QuizStartUiState> = repository.quizStartUiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = initialState
        )

    fun setAnatomyArea(anatomyArea: String) {
        viewModelScope.launch {
            repository.setAnatomyArea(anatomyArea)
        }
    }

    fun setLanguage(language: Language) {
        viewModelScope.launch {
            repository.setLanguage(language)
        }
    }

    fun setQuizMode(mode: QuizMode) {
        viewModelScope.launch {
            repository.setQuizMode(mode)
        }
    }
}
