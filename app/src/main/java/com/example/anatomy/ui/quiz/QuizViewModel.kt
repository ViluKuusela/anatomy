package com.example.anatomy.ui.quiz

import androidx.lifecycle.ViewModel
import com.example.anatomy.data.Bone
import com.example.anatomy.data.QuizSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel(
    bones: List<Bone>
) : ViewModel() {

    private val _session = MutableStateFlow(
        QuizSession(
            remainingBones = bones.shuffled(),
            currentBone = bones.firstOrNull(),
            correctCount = 0,
            totalCount = bones.size
        )
    )
    val session: StateFlow<QuizSession> = _session

    private val _selectedAnswer = MutableStateFlow<Bone?>(null)
    val selectedAnswer: StateFlow<Bone?> = _selectedAnswer

    fun selectAnswer(bone: Bone) {
        if (_selectedAnswer.value != null) return

        _selectedAnswer.value = bone

        if (bone.id == _session.value.currentBone?.id) {
            _session.update {
                it.copy(correctCount = it.correctCount + 1)
            }
        }
    }

    fun advance() {
        val rem = _session.value.remainingBones.toMutableList()

        // Remove current bone from the list
        _session.value.currentBone?.let { rem.remove(it) }

        _session.value = _session.value.copy(
            remainingBones = rem,
            currentBone = rem.firstOrNull(),
        )
        _selectedAnswer.value = null
    }

    fun restart(allBones: List<Bone>) {
        _session.value = QuizSession(
            remainingBones = allBones.shuffled(),
            currentBone = allBones.firstOrNull(),
            correctCount = 0,
            totalCount = allBones.size
        )
        _selectedAnswer.value = null
    }
}
