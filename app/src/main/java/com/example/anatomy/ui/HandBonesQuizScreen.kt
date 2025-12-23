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

@Composable
fun HandBonesQuizScreen() {

    val bones = BoneRepository.handBones
    var currentBone by remember { mutableStateOf(bones.random()) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }

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
            contentDescription = currentBone.latinName,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        options.forEach { bone ->
            AnswerButton(
                text = bone.latinName,
                correctAnswer = currentBone.latinName,
                selectedAnswer = selectedAnswer,
                onClick = { selectedAnswer = bone.latinName }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedAnswer != null) {
            Button(onClick = {
                selectedAnswer = null
                currentBone = bones.random()
            }) {
                Text("Next")
            }
        }
    }
}
