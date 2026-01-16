package com.example.anatomy.ui.quiz

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import com.example.anatomy.data.BoneRepository
import com.example.anatomy.data.settings.QuizMode
import com.example.anatomy.ui.components.AnswerButton
import com.example.anatomy.ui.components.BoneImage
import com.example.anatomy.ui.language.Language
import com.example.anatomy.ui.settings.SettingsViewModel
import com.example.anatomy.ui.theme.CorrectAnswerColor
import com.example.anatomy.ui.theme.FalseAnswerColor
import com.example.anatomy.ui.theme.QuestionHighlightColor
import kotlinx.coroutines.delay

/**
 * This composable function represents the main quiz screen.
 */
@Composable
fun QuizScreen(
    anatomyArea: String,
    language: Language,
    quizMode: QuizMode,
    settingsViewModel: SettingsViewModel,
    onOpenSettings: () -> Unit,
    onFinish: () -> Unit
) {
    val bones = BoneRepository.getBones(anatomyArea)
    val viewModel: QuizViewModel = viewModel { QuizViewModel(bones) }
    val context = LocalContext.current

    val session by viewModel.session.collectAsState()
    val answerResult by viewModel.answerResult.collectAsState()
    val settingsState by settingsViewModel.uiState.collectAsState()
    val incorrectBones by viewModel.incorrectBones.collectAsState()

    var countdown by remember { mutableStateOf(0) }
    var showEndSessionDialog by remember { mutableStateOf(false) }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    // Pre-calculate bitmaps for TAP mode hit testing
    val boneMasks = remember(bones) {
        bones.associate { bone ->
            val drawable = ContextCompat.getDrawable(context, bone.highlightMaskRes)!!
            val bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bone.id to bitmap
        }
    }

    if (showEndSessionDialog) {
        AlertDialog(
            onDismissRequest = { showEndSessionDialog = false },
            title = { Text("End Session") },
            confirmButton = { TextButton(onClick = { onFinish() }) { Text("Confirm") } },
            dismissButton = { TextButton(onClick = { showEndSessionDialog = false }) { Text("Cancel") } }
        )
    }

    LaunchedEffect(answerResult) {
        if (settingsState.enableAutoAdvance && answerResult is AnswerResult.Answered) {
            countdown = settingsState.autoNextDelaySeconds
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
            viewModel.advance()
        }
    }

    if (session.currentBone == null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Session Complete", style = MaterialTheme.typography.headlineMedium)
            Text("${session.correctCount} / ${session.totalCount} correct")
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { viewModel.restart() }, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("Restart Full Session")
            }
            if (incorrectBones.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.reviewIncorrect() }, modifier = Modifier.fillMaxWidth(0.7f), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                    Text("Review Incorrect (${incorrectBones.size})")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = onFinish, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("Finish Session")
            }
        }
        return
    }

    val currentBone = session.currentBone!!
    val result = answerResult as? AnswerResult.Answered
    val options = remember(currentBone) {
        (bones - currentBone).shuffled().take(3).plus(currentBone).shuffled()
    }
    var writtenAnswer by remember { mutableStateOf("") }
    LaunchedEffect(currentBone) { writtenAnswer = "" }

    val answeredCount = session.totalCount - session.remainingBones.size
    val progress = answeredCount.toFloat() / session.totalCount

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        // --- HEADER ---
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = onOpenSettings) { Icon(Icons.Default.Settings, "Settings") }
            IconButton(onClick = { showEndSessionDialog = true }) { Icon(Icons.Default.Close, "End") }
        }
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(8.dp), progress = { progress }, color = MaterialTheme.colorScheme.primary, trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
        
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$answeredCount / ${session.totalCount}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(16.dp))
        
        // --- QUESTION HEADER (Fixed height) ---
        Box(modifier = Modifier.height(60.dp), contentAlignment = Alignment.Center) {
            if (quizMode == QuizMode.TAP) {
                Text("Tap ${currentBone.getName(language)}", style = MaterialTheme.typography.headlineSmall)
            }
        }

        // --- IMAGE AREA (Pixel perfect hit testing) ---
        val firstBoneRef = bones.firstOrNull()
        val baseDrawableRef = remember(anatomyArea) {
            if (firstBoneRef != null) ContextCompat.getDrawable(context, firstBoneRef.baseDrawableRes) else null
        }
        val imgWidth = baseDrawableRef?.intrinsicWidth?.toFloat() ?: 1f
        val imgHeight = baseDrawableRef?.intrinsicHeight?.toFloat() ?: 1f
        val imgAspectRatio = imgWidth / imgHeight

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .onGloballyPositioned { boxSize = it.size }
                .pointerInput(currentBone, answerResult) {
                    if (quizMode == QuizMode.TAP && answerResult is AnswerResult.Unanswered) {
                        detectTapGestures { offset ->
                            if (boxSize.width > 0 && boxSize.height > 0) {
                                val containerW = size.width.toFloat()
                                val containerH = size.height.toFloat()
                                val containerRatio = containerW / containerH
                                val (drawnW, drawnH) = if (containerRatio > imgAspectRatio) (containerH * imgAspectRatio) to containerH else containerW to (containerW / imgAspectRatio)
                                val left = (containerW - drawnW) / 2
                                val top = (containerH - drawnH) / 2
                                if (offset.x in left..(left + drawnW) && offset.y in top..(top + drawnH)) {
                                    val normX = (offset.x - left) / drawnW
                                    val normY = (offset.y - top) / drawnH
                                    val hitBones = bones.filter { bone ->
                                        val bmp = boneMasks[bone.id] ?: return@filter false
                                        val px = (normX * bmp.width).toInt().coerceIn(0, bmp.width - 1)
                                        val py = (normY * bmp.height).toInt().coerceIn(0, bmp.height - 1)
                                        android.graphics.Color.alpha(bmp.getPixel(px, py)) > 0
                                    }
                                    if (hitBones.isNotEmpty()) {
                                        val selected = if (hitBones.any { it.id == currentBone.id }) currentBone else hitBones.first()
                                        viewModel.selectAnswer(selected)
                                    }
                                }
                            }
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            val highlightColor = when (val res = answerResult) {
                is AnswerResult.Answered -> if (res.wasCorrect) CorrectAnswerColor else {
                    if (quizMode == QuizMode.TAP) CorrectAnswerColor else FalseAnswerColor
                }
                is AnswerResult.Unanswered -> if (quizMode == QuizMode.TAP) Color.Transparent else QuestionHighlightColor
            }
            val errorBone = if (quizMode == QuizMode.TAP && result != null && !result.wasCorrect) result.selectedOption else null

            BoneImage(
                bone = currentBone,
                highlightColor = highlightColor,
                errorBone = errorBone,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(Modifier.height(16.dp))

        // --- INTERACTION AREA (Fixed size to prevent jumps) ---
        Box(modifier = Modifier.fillMaxWidth().height(220.dp), contentAlignment = Alignment.TopCenter) {
            when (quizMode) {
                QuizMode.CHOOSE -> {
                    Column {
                        options.forEach { bone ->
                            val isAnswered = answerResult is AnswerResult.Answered
                            AnswerButton(
                                text = bone.getName(language),
                                isSelected = result?.selectedOption == bone,
                                isCorrect = isAnswered && bone.id == currentBone.id,
                                enabled = !isAnswered,
                                onClick = { viewModel.selectAnswer(bone) }
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
                QuizMode.WRITE -> {
                    val isAnswered = answerResult is AnswerResult.Answered
                    val wasCorrect = result?.wasCorrect == true
                    Column {
                        OutlinedTextField(
                            value = writtenAnswer,
                            onValueChange = { writtenAnswer = it },
                            label = { Text("Enter bone name in ${language.name.lowercase()}") },
                            singleLine = true,
                            enabled = !isAnswered,
                            modifier = Modifier.fillMaxWidth(),
                            colors = if (isAnswered) {
                                val color = if (wasCorrect) CorrectAnswerColor else FalseAnswerColor
                                OutlinedTextFieldDefaults.colors(disabledBorderColor = color, disabledTextColor = color, disabledLabelColor = color)
                            } else { OutlinedTextFieldDefaults.colors() }
                        )
                        if (isAnswered) {
                            Text(
                                text = if (wasCorrect) "Correct!" else "Correct answer: ${currentBone.getName(language)}",
                                color = if (wasCorrect) CorrectAnswerColor else FalseAnswerColor,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
                QuizMode.TAP -> {
                    if (result != null) {
                        Text(
                            text = if (result.wasCorrect) "Correct!" else "Incorrect! You tapped: ${result.selectedOption?.getName(language)}",
                            color = if (result.wasCorrect) CorrectAnswerColor else FalseAnswerColor,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        // --- NAVIGATION BUTTON ---
        Box(modifier = Modifier.height(48.dp), contentAlignment = Alignment.Center) {
            if (answerResult is AnswerResult.Answered) {
                Button(onClick = viewModel::advance) {
                    val nextButtonText = if (session.remainingBones.size == 1) "Finish" else "Next"
                    Text(if (settingsState.enableAutoAdvance) "$nextButtonText ($countdown)" else nextButtonText)
                }
            } else if (quizMode == QuizMode.WRITE) {
                Button(onClick = { viewModel.submitWrittenAnswer(writtenAnswer, language) }, enabled = writtenAnswer.isNotBlank()) {
                    Text("Submit")
                }
            }
        }
    }
}
