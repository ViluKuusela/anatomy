package com.example.anatomy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.anatomy.data.Bone
import com.example.anatomy.data.BoneRepository
import com.example.anatomy.data.Language

@Composable
fun HandBonesQuizScreen() {

    val bones = BoneRepository.handBones
    val currentLanguage = Language.LATIN
    var currentBone by remember { mutableStateOf(bones.random()) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    val correctAnswerText = currentBone.getName(currentLanguage)

    val options = remember(currentBone) {
        (bones - currentBone)
            .shuffled()
            .take(3)
            .plus(currentBone)
            .shuffled()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(currentBone.drawableRes),
            contentDescription = currentBone.getName(currentLanguage),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        options.forEach { bone ->
            AnswerButton(
                text = bone.getName(currentLanguage),
                correctAnswer = currentBone.getName(currentLanguage),
                selectedAnswer = selectedAnswer,
                onClick = { selectedAnswer = bone.getName(currentLanguage) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedAnswer != null) {
            Button(onClick = {
                selectedAnswer = null
                currentBone = getNextBone(bones, currentBone)
            }) {
                Text("Next")
            }
        }
    }
}

fun getNextBone(
    bones: List<Bone>,
    currentBone: Bone
): Bone {
    if (bones.size <= 1) return currentBone

    var next: Bone
    do {
        next = bones.random()
    } while (next.id == currentBone.id)

    return next
}
