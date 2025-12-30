package com.example.anatomy.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import com.example.anatomy.ui.settings.SettingsViewModel
import kotlinx.coroutines.delay

/**
 * This composable function represents the main quiz screen.
 * It displays the current bone, answer options, and progress.
 *
 * @param anatomyArea The selected anatomy area for the quiz (e.g., "Hand", "Foot").
 * @param language The selected language for the bone names.
 * @param settingsViewModel The ViewModel for accessing and managing user settings.
 * @param onOpenSettings A callback function to be invoked when the user clicks the settings button.
 */
@Composable
fun QuizScreen(
    anatomyArea: String,
    language: Language,
    settingsViewModel: SettingsViewModel,
    onOpenSettings: () -> Unit
) {
    // Get the list of bones for the selected anatomy area from the repository.
    val bones = BoneRepository.getBones(anatomyArea)
    // Create an instance of the QuizViewModel, providing the list of bones.
    val viewModel: QuizViewModel = viewModel {
        QuizViewModel(bones)
    }

    // Collect state from the ViewModels as State objects.
    // This ensures that the UI recomposes whenever the state changes.
    val session by viewModel.session.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()
    val settingsState by settingsViewModel.uiState.collectAsState()

    // State for the auto-advance countdown timer.
    var countdown by remember { mutableStateOf(0) }

    // This LaunchedEffect handles the auto-advance feature.
    // It triggers when an answer is selected or when the auto-advance setting changes.
    LaunchedEffect(selectedAnswer, settingsState) {
        if (settingsState.enableAutoAdvance && selectedAnswer != null) {
            countdown = settingsState.autoNextDelaySeconds
            while (countdown > 0) {
                delay(1000) // wait for 1 second
                countdown--
            }
            viewModel.advance() // Automatically move to the next question.
        }
    }

    // If there are no more bones in the session, display the "Session Complete" screen.
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

            // Button to restart the quiz session with the same set of bones.
            Button(onClick = { viewModel.restart(bones) }) {
                Text("Restart session")
            }
        }
        return // Stop rendering the rest of the quiz screen.
    }

    val currentBone = session.currentBone!!

    // Generate answer options for the current bone.
    // It includes the correct bone and three other random bones from the same list.
    val options = remember(currentBone) {
        (bones - currentBone)
            .shuffled()
            .take(3)
            .plus(currentBone)
            .shuffled()
    }

    // Calculate the quiz progress.
    val answeredCount = session.totalCount - session.remainingBones.size
    val progress = answeredCount.toFloat() / session.totalCount

    // Main UI layout for the quiz screen.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Settings button.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onOpenSettings) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        }

        // Progress bar to show quiz progress.
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

        // Image of the bone to be identified.
        BoneImage(
            bone = currentBone,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display the answer buttons.
        options.forEach { bone ->
            val isSelected = selectedAnswer == bone
            val isCorrect = selectedAnswer != null && bone.id == currentBone.id

            AnswerButton(
                text = bone.getName(language),
                isSelected = isSelected,
                isCorrect = isCorrect,
                enabled = selectedAnswer == null, // Disable buttons after an answer is selected.
                onClick = { viewModel.selectAnswer(bone) }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // The "Next" button is displayed after an answer is selected.
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

                    // If auto-advance is enabled, show the countdown in the button text.
                    if (settingsState.enableAutoAdvance && selectedAnswer != null) {
                        Text("$nextButtonText ($countdown)")
                    } else {
                        Text(nextButtonText)
                    }
                }
            }
        }
    }
}
