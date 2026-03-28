package com.viluappdev.anatomy.ui.quiz

import androidx.lifecycle.ViewModel
import com.viluappdev.anatomy.data.Bone
import com.viluappdev.anatomy.data.QuizSession
import com.viluappdev.anatomy.ui.language.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Represents the result of an answer submission.
sealed class AnswerResult {
    object Unanswered : AnswerResult()
    data class Answered(val wasCorrect: Boolean, val selectedOption: Bone? = null, val wasRevealed: Boolean = false) : AnswerResult()
}

class QuizViewModel(
    private val allBones: List<Bone>
) : ViewModel() {

    private val _session = MutableStateFlow(
        QuizSession(
            remainingBones = allBones.shuffled(),
            currentBone = allBones.shuffled().firstOrNull(),
            correctCount = 0,
            totalCount = allBones.size
        )
    )
    val session: StateFlow<QuizSession> = _session

    private val _answerResult = MutableStateFlow<AnswerResult>(AnswerResult.Unanswered)
    val answerResult: StateFlow<AnswerResult> = _answerResult

    private val _incorrectBones = MutableStateFlow<List<Bone>>(emptyList())
    val incorrectBones: StateFlow<List<Bone>> = _incorrectBones

    private val _isHintActive = MutableStateFlow(false)
    val isHintActive: StateFlow<Boolean> = _isHintActive

    private val _helpUsedForCurrent = MutableStateFlow(false)

    fun selectAnswer(bone: Bone) {
        if (_answerResult.value is AnswerResult.Answered) return

        val currentBone = _session.value.currentBone ?: return
        val isCorrect = bone.id == currentBone.id
        
        val countAsCorrect = isCorrect && !_helpUsedForCurrent.value

        if (countAsCorrect) {
            _session.update { it.copy(correctCount = it.correctCount + 1) }
        } else if (!isCorrect && !_helpUsedForCurrent.value) {
            _incorrectBones.update { it + currentBone }
        }
        
        _answerResult.value = AnswerResult.Answered(isCorrect, bone, wasRevealed = _helpUsedForCurrent.value)
    }

    fun submitWrittenAnswer(answer: String, language: Language) {
        if (_answerResult.value is AnswerResult.Answered) return

        val currentBone = _session.value.currentBone ?: return
        val acceptedNames = currentBone.getAllNames(language)
        val isCorrect = acceptedNames.any { it.equals(answer.trim(), ignoreCase = true) }

        val countAsCorrect = isCorrect && !_helpUsedForCurrent.value

        if (countAsCorrect) {
            _session.update { it.copy(correctCount = it.correctCount + 1) }
        } else if (!isCorrect && !_helpUsedForCurrent.value) {
            _incorrectBones.update { it + currentBone }
        }

        _answerResult.value = AnswerResult.Answered(isCorrect, wasRevealed = _helpUsedForCurrent.value)
    }

    fun setHintVisible(visible: Boolean) {
        if (_answerResult.value is AnswerResult.Answered) return
        
        _isHintActive.value = visible
        if (visible && !_helpUsedForCurrent.value) {
            _helpUsedForCurrent.value = true
            val currentBone = _session.value.currentBone ?: return
            if (!_incorrectBones.value.contains(currentBone)) {
                _incorrectBones.update { it + currentBone }
            }
        }
    }

    fun skip() {
        if (_answerResult.value is AnswerResult.Answered) return
        
        val currentBone = _session.value.currentBone ?: return
        // Mark as incorrect if not already marked
        if (!_incorrectBones.value.contains(currentBone)) {
            _incorrectBones.update { it + currentBone }
        }
        
        advance()
    }

    fun advance() {
        val remaining = _session.value.remainingBones.toMutableList()
        _session.value.currentBone?.let { remaining.remove(it) }

        _session.update {
            it.copy(
                remainingBones = remaining,
                currentBone = remaining.firstOrNull(),
            )
        }
        _answerResult.value = AnswerResult.Unanswered
        _helpUsedForCurrent.value = false
        _isHintActive.value = false
    }

    fun restart() {
        _session.value = QuizSession(
            remainingBones = allBones.shuffled(),
            currentBone = allBones.shuffled().firstOrNull(),
            correctCount = 0,
            totalCount = allBones.size
        )
        _answerResult.value = AnswerResult.Unanswered
        _incorrectBones.value = emptyList()
        _helpUsedForCurrent.value = false
        _isHintActive.value = false
    }

    fun reviewIncorrect() {
        val bonesToReview = _incorrectBones.value.shuffled()
        if (bonesToReview.isEmpty()) return

        _session.value = QuizSession(
            remainingBones = bonesToReview,
            currentBone = bonesToReview.firstOrNull(),
            correctCount = 0,
            totalCount = bonesToReview.size
        )
        _answerResult.value = AnswerResult.Unanswered
        _incorrectBones.value = emptyList()
        _helpUsedForCurrent.value = false
        _isHintActive.value = false
    }
}
