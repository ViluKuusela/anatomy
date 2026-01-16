package com.example.anatomy.ui.quiz

import androidx.lifecycle.ViewModel
import com.example.anatomy.data.Bone
import com.example.anatomy.data.QuizSession
import com.example.anatomy.ui.language.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Represents the result of an answer submission.
sealed class AnswerResult {
    // The user has not answered the question yet.
    object Unanswered : AnswerResult()

    // The user has answered the question.
    data class Answered(val wasCorrect: Boolean, val selectedOption: Bone? = null) : AnswerResult()
}

class QuizViewModel(
    private val allBones: List<Bone> // Keep a private copy of all bones for the session
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

    // Track bones that were answered incorrectly
    private val _incorrectBones = MutableStateFlow<List<Bone>>(emptyList())
    val incorrectBones: StateFlow<List<Bone>> = _incorrectBones

    /**
     * Processes a user's selection in a multiple-choice question.
     */
    fun selectAnswer(bone: Bone) {
        if (_answerResult.value is AnswerResult.Answered) return

        val currentBone = _session.value.currentBone ?: return
        val isCorrect = bone.id == currentBone.id
        
        if (isCorrect) {
            _session.update {
                it.copy(correctCount = it.correctCount + 1)
            }
        } else {
            _incorrectBones.update { it + currentBone }
        }
        _answerResult.value = AnswerResult.Answered(isCorrect, bone)
    }

    /**
     * Processes a user's written answer.
     */
    fun submitWrittenAnswer(answer: String, language: Language) {
        if (_answerResult.value is AnswerResult.Answered) return

        val currentBone = _session.value.currentBone ?: return
        val correctAnswer = currentBone.getName(language)

        val isCorrect = answer.equals(correctAnswer, ignoreCase = true)

        if (isCorrect) {
            _session.update { it.copy(correctCount = it.correctCount + 1) }
        } else {
            _incorrectBones.update { it + currentBone }
        }

        _answerResult.value = AnswerResult.Answered(isCorrect)
    }

    /**
     * Advances to the next question in the quiz.
     */
    fun advance() {
        val remaining = _session.value.remainingBones.toMutableList()

        // Remove current bone from the list
        _session.value.currentBone?.let { remaining.remove(it) }

        _session.update {
            it.copy(
                remainingBones = remaining,
                currentBone = remaining.firstOrNull(),
            )
        }
        _answerResult.value = AnswerResult.Unanswered
    }

    /**
     * Restarts the quiz with the full list of bones.
     */
    fun restart() {
        _session.value = QuizSession(
            remainingBones = allBones.shuffled(),
            currentBone = allBones.shuffled().firstOrNull(),
            correctCount = 0,
            totalCount = allBones.size
        )
        _answerResult.value = AnswerResult.Unanswered
        _incorrectBones.value = emptyList()
    }

    /**
     * Starts a new session containing only the bones answered incorrectly.
     */
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
    }
}
