package com.example.anatomy.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*

import com.example.anatomy.data.BoneRepository
import com.example.anatomy.ui.components.AnswerButton
import com.example.anatomy.ui.components.BoneImage
import com.example.anatomy.ui.language.Language
import kotlinx.coroutines.delay

@Composable
fun HandBonesQuizScreen(
    language: Language = Language.LATIN
) {
    val viewModel: QuizViewModel = viewModel {
        QuizViewModel(BoneRepository.handBones)
    }

    val session by viewModel.session.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()

    val autoNextSeconds = 3
    var countdown by remember { mutableStateOf(autoNextSeconds) }

    // ---------- Auto advance timer ----------
    LaunchedEffect(selectedAnswer) {
        if (selectedAnswer != null) {
            countdown = autoNextSeconds
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            viewModel.advance()
        }
    }

    // ---------- Session complete ----------
    if (session.currentBone == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Session Complete", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("${session.correctCount} / ${session.totalCount} correct")

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { viewModel.restart(BoneRepository.handBones) }) {
                Text("Restart session")
            }
        }
        return
    }

    val currentBone = session.currentBone!!

    // ---------- Options ----------
    val options = remember(currentBone) {
        (BoneRepository.handBones - currentBone)
            .shuffled()
            .take(3)
            .plus(currentBone)
            .shuffled()
    }

    val answeredCount = session.totalCount - session.remainingBones.size
    val progress = answeredCount.toFloat() / session.totalCount

    // ---------- UI ----------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Progress bar
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            progress = { progress },
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )


        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "$answeredCount / ${session.totalCount} answered",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bone image
        BoneImage(
            bone = currentBone,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Answer buttons
        options.forEach { bone ->
            val isSelected = selectedAnswer == bone
            val isCorrect = selectedAnswer != null && bone.id == currentBone.id

            AnswerButton(
                text = bone.getName(language),
                isSelected = isSelected,
                isCorrect = isCorrect,
                enabled = selectedAnswer == null,
                onClick = { viewModel.selectAnswer(bone) }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Next button (centered)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            contentAlignment = Alignment.Center
        ) {

            if (selectedAnswer != null) {
                Button(onClick = viewModel::advance) {
                    val isLastQuestion = session.remainingBones.size == 1
                    val nextButtonText = if (isLastQuestion) "Finish" else "Next"
                    Text("$nextButtonText ($countdown)")
                }
            }
        }
    }
}
