package com.example.anatomy.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import com.example.anatomy.data.BoneRepository
import com.example.anatomy.ui.components.AnswerButton
import com.example.anatomy.ui.components.BoneImage
import com.example.anatomy.ui.language.Language
import com.example.anatomy.ui.settings.SettingsViewModel
import com.example.anatomy.ui.theme.CorrectAnswerColor
import com.example.anatomy.ui.theme.FalseAnswerColor
import kotlinx.coroutines.delay

/**
 * This composable function represents the main quiz screen.
 * It displays the current bone, answer options, and progress.
 *
 * @param anatomyArea The selected anatomy area for the quiz (e.g., "Hand", "Foot").
 * @param language The selected language for the bone names.
 * @param isMultipleChoice A boolean that determines if the quiz is multiple choice or written answer.
 * @param settingsViewModel The ViewModel for accessing and managing user settings.
 * @param onOpenSettings A callback function to be invoked when the user clicks the settings button.
 * @param onFinish A callback function to be invoked when the user finishes the session.
 */
@Composable
fun QuizScreen(
    anatomyArea: String,
    language: Language,
    isMultipleChoice: Boolean,
    settingsViewModel: SettingsViewModel,
    onOpenSettings: () -> Unit,
    onFinish: () -> Unit
) {
    // Get the list of bones for the selected anatomy area from the repository.
    val bones = BoneRepository.getBones(anatomyArea)
    // Create an instance of the QuizViewModel, providing the list of bones.
    val viewModel: QuizViewModel = viewModel {
        QuizViewModel(bones)
    }

    // Collect state from the ViewModels as State objects.
    val session by viewModel.session.collectAsState()
    val answerResult by viewModel.answerResult.collectAsState()
    val settingsState by settingsViewModel.uiState.collectAsState()
    val incorrectBones by viewModel.incorrectBones.collectAsState()

    // State for the auto-advance countdown timer.
    var countdown by remember { mutableStateOf(0) }
    var showEndSessionDialog by remember { mutableStateOf(false) }

    if (showEndSessionDialog) {
        AlertDialog(
            onDismissRequest = { showEndSessionDialog = false },
            title = { Text("End Session") },
            text = { Text("Are you sure you want to end the current session?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showEndSessionDialog = false
                        onFinish() // Call the onFinish lambda to navigate back
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEndSessionDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    // This LaunchedEffect handles the auto-advance feature.
    LaunchedEffect(answerResult, settingsState) {
        if (settingsState.enableAutoAdvance && answerResult is AnswerResult.Answered) {
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

            Button(
                onClick = { viewModel.restart() },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Restart Full Session")
            }

            if (incorrectBones.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.reviewIncorrect() },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Review Incorrect (${incorrectBones.size})")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onFinish,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Finish Session")
            }
        }
        return // Stop rendering the rest of the quiz screen.
    }

    val currentBone = session.currentBone!!

    // Generate answer options for the current bone.
    val options = remember(currentBone) {
        (bones - currentBone)
            .shuffled()
            .take(3)
            .plus(currentBone)
            .shuffled()
    }

    val answeredCount = session.totalCount - session.remainingBones.size
    val progress = answeredCount.toFloat() / session.totalCount

    var writtenAnswer by remember { mutableStateOf("") }

    // Reset written answer when the question changes
    LaunchedEffect(currentBone) {
        writtenAnswer = ""
    }

    // Main UI layout for the quiz screen.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top action buttons: Settings and End Session
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onOpenSettings) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }

            IconButton(onClick = { showEndSessionDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "End Session"
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

        // Determine the highlight color based on the answer result.
        val highlightColor = when (val result = answerResult) {
            is AnswerResult.Answered -> if (result.wasCorrect) CorrectAnswerColor else FalseAnswerColor
            is AnswerResult.Unanswered -> CorrectAnswerColor // Use green for the question mask as requested
        }

        // Image of the bone to be identified.
        BoneImage(
            bone = currentBone,
            highlightColor = highlightColor,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        if (isMultipleChoice) {
            // Display the answer buttons for multiple choice.
            options.forEach { bone ->
                val isSelected =
                    (answerResult as? AnswerResult.Answered)?.selectedOption == bone
                val isCorrect = answerResult is AnswerResult.Answered && bone.id == currentBone.id

                AnswerButton(
                    text = bone.getName(language),
                    isSelected = isSelected,
                    isCorrect = isCorrect,
                    enabled = answerResult is AnswerResult.Unanswered, // Disable buttons after an answer is selected.
                    onClick = { viewModel.selectAnswer(bone) }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {
            // Display the text field for written answers.
            val isAnswered = answerResult is AnswerResult.Answered
            val wasCorrect = (answerResult as? AnswerResult.Answered)?.wasCorrect == true

            val textFieldColors = if (isAnswered) {
                val color = if (wasCorrect) CorrectAnswerColor else FalseAnswerColor
                OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = color,
                    disabledTextColor = color,
                    disabledLabelColor = color
                )
            } else {
                OutlinedTextFieldDefaults.colors()
            }

            OutlinedTextField(
                value = writtenAnswer,
                onValueChange = { writtenAnswer = it },
                label = { Text("Enter bone name in ${language.name.lowercase().replaceFirstChar { it.uppercase() }}") },
                singleLine = true,
                enabled = !isAnswered,
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors
            )

            // Reserve space for the correct/incorrect answer text to prevent layout shifts.
            Box(modifier = Modifier.defaultMinSize(minHeight = 24.dp)) {
                if (isAnswered) {
                    if (wasCorrect) {
                        Text(
                            text = "Correct!",
                            color = CorrectAnswerColor,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    } else {
                        Text(
                            text = "Correct answer: ${currentBone.getName(language)}",
                            color = FalseAnswerColor,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // This Box serves as a stable container for the action buttons (Submit/Next).
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            contentAlignment = Alignment.Center
        ) {
            val isAnswered = answerResult is AnswerResult.Answered
            if (isAnswered) {
                // If an answer has been given, show the "Next" button.
                Button(onClick = viewModel::advance) {
                    val isLastQuestion = session.remainingBones.size == 1
                    val nextButtonText = if (isLastQuestion) "Finish" else "Next"

                    if (settingsState.enableAutoAdvance) {
                        Text("$nextButtonText ($countdown)")
                    } else {
                        Text(nextButtonText)
                    }
                }
            } else {
                // If no answer has been given yet...
                if (!isMultipleChoice) {
                    // ...and it's a written answer quiz, show the "Submit" button.
                    Button(
                        onClick = { viewModel.submitWrittenAnswer(writtenAnswer, language) },
                        enabled = writtenAnswer.isNotBlank()
                    ) {
                        Text("Submit")
                    }
                } // In multiple choice mode, this box remains empty, as answer buttons are shown above.
            }
        }
    }
}
