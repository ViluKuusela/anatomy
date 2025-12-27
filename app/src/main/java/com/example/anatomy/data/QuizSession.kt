package com.example.anatomy.data

data class QuizSession(
    val remainingBones: List<Bone>,
    val currentBone: Bone?,
    val correctCount: Int,
    val totalCount: Int
)
