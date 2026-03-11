package com.example.anatomy.ui.quiz

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import com.example.anatomy.R
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
    val haptic = LocalHapticFeedback.current

    val session by viewModel.session.collectAsState()
    val answerResult by viewModel.answerResult.collectAsState()
    val settingsState by settingsViewModel.uiState.collectAsState()
    val incorrectBones by viewModel.incorrectBones.collectAsState()

    var countdown by remember { mutableIntStateOf(0) }
    var showEndSessionDialog by remember { mutableStateOf(false) }
    var isExiting by remember { mutableStateOf(false) }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    // Zoom and pan states
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    // Session progress info
    val answeredCount = session.totalCount - session.remainingBones.size
    val progress = if (session.totalCount > 0) answeredCount.toFloat() / session.totalCount else 0f
    
    // Animated progress for smoother UI
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500),
        label = "progressAnimation"
    )

    // Helper to handle exit request (with or without confirmation)
    val handleExit = {
        if (!isExiting) {
            if (answeredCount > 0) {
                showEndSessionDialog = true
            } else {
                isExiting = true
                onFinish()
            }
        }
    }

    // Handle Android system back button/gesture
    BackHandler(onBack = handleExit)

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
            title = { Text(stringResource(R.string.quiz_exit_title)) },
            text = { Text(stringResource(R.string.quiz_exit_message)) },
            confirmButton = { 
                TextButton(onClick = { 
                    if (!isExiting) {
                        isExiting = true
                        onFinish()
                    }
                }) { Text(stringResource(R.string.quiz_confirm)) } 
            },
            dismissButton = { 
                TextButton(onClick = { showEndSessionDialog = false }) { Text(stringResource(R.string.quiz_cancel)) } 
            }
        )
    }

    LaunchedEffect(answerResult) {
        if (answerResult is AnswerResult.Answered) {
            val res = answerResult as AnswerResult.Answered
            if (!res.wasCorrect) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
            
            if (settingsState.enableAutoAdvance) {
                countdown = settingsState.autoNextDelaySeconds
                while (countdown > 0) {
                    delay(1000)
                    countdown--
                }
                viewModel.advance()
            }
        }
    }

    if (session.currentBone == null) {
        Column(
            modifier = Modifier.fillMaxSize().systemBarsPadding().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.quiz_session_complete), style = MaterialTheme.typography.headlineMedium)
            Text(stringResource(R.string.quiz_correct_count, session.correctCount, session.totalCount))
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { viewModel.restart() }, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(stringResource(R.string.quiz_restart))
            }
            if (incorrectBones.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.reviewIncorrect() }, modifier = Modifier.fillMaxWidth(0.7f), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                    Text(stringResource(R.string.quiz_review_incorrect, incorrectBones.size))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = {
                if (!isExiting) {
                    isExiting = true
                    onFinish()
                }
            }, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text(stringResource(R.string.quiz_finish_session))
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
    
    // Controlled color and alpha for the highlight. 
    // Using key(currentBone) forces a complete reset of these states when question changes.
    val (highlightColorState, highlightAlphaState) = key(currentBone) {
        val color = remember { Animatable(QuestionHighlightColor) }
        val alpha = remember { Animatable(0f) }
        color to alpha
    }

    LaunchedEffect(currentBone) { 
        writtenAnswer = "" 
        scale = 1f
        offset = Offset.Zero
        
        // Ensure color is reset and then fade in
        if (quizMode != QuizMode.TAP) {
            highlightAlphaState.animateTo(1f, animationSpec = tween(durationMillis = 600))
        }
    }

    // Update color only when an answer is given
    LaunchedEffect(answerResult) {
        if (answerResult is AnswerResult.Answered) {
            val res = answerResult as AnswerResult.Answered
            val target = if (res.wasCorrect) CorrectAnswerColor else FalseAnswerColor
            highlightAlphaState.snapTo(1f) // Ensure fully visible for results
            highlightColorState.animateTo(target, animationSpec = tween(300))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- COMPACT HEADER ---
        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onOpenSettings) { 
                Icon(Icons.Default.Settings, stringResource(R.string.cd_settings), modifier = Modifier.size(20.dp)) 
            }
            
            // Custom thick progress bar with text inside
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .height(22.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(animatedProgress)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = stringResource(R.string.quiz_correct_count, answeredCount, session.totalCount),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = if (animatedProgress > 0.5f) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            IconButton(onClick = { handleExit() }) { 
                Icon(Icons.Default.Close, stringResource(R.string.cd_end_quiz), modifier = Modifier.size(20.dp)) 
            }
        }

        Spacer(Modifier.height(4.dp))

        // --- IMAGE AREA ---
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
                .clipToBounds() // This ensures zoomed content doesn't bleed over other UI elements
                .onGloballyPositioned { boxSize = it.size }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale = (scale * zoom).coerceIn(1f, 5f)
                        if (scale > 1f) {
                            offset += pan
                        } else {
                            offset = Offset.Zero
                        }
                    }
                }
                .pointerInput(currentBone, answerResult, scale, offset) {
                    if (quizMode == QuizMode.TAP && answerResult is AnswerResult.Unanswered) {
                        detectTapGestures { tapOffset ->
                            if (boxSize.width > 0 && boxSize.height > 0) {
                                val containerW = size.width.toFloat()
                                val containerH = size.height.toFloat()
                                
                                // Map tap back to original coordinate system for hit testing
                                val cx = containerW / 2f
                                val cy = containerH / 2f
                                val origX = cx + (tapOffset.x - cx - offset.x) / scale
                                val origY = cy + (tapOffset.y - cy - offset.y) / scale
                                
                                val containerRatio = containerW / containerH
                                val (drawnW, drawnH) = if (containerRatio > imgAspectRatio) (containerH * imgAspectRatio) to containerH else containerW to (containerW / imgAspectRatio)
                                val left = (containerW - drawnW) / 2
                                val top = (containerH - drawnH) / 2
                                
                                if (origX in left..(left + drawnW) && origY in top..(top + drawnH)) {
                                    val normX = (origX - left) / drawnW
                                    val normY = (origY - top) / drawnH
                                    
                                    val radius = 4
                                    val hitBones = bones.filter { bone ->
                                        val bmp = boneMasks[bone.id] ?: return@filter false
                                        val centerX = (normX * bmp.width).toInt()
                                        val centerY = (normY * bmp.height).toInt()
                                        
                                        var found = false
                                        for (dx in -radius..radius) {
                                            for (dy in -radius..radius) {
                                                val px = (centerX + dx).coerceIn(0, bmp.width - 1)
                                                val py = (centerY + dy).coerceIn(0, bmp.height - 1)
                                                if (android.graphics.Color.alpha(bmp.getPixel(px, py)) > 0) {
                                                    found = true
                                                    break
                                                }
                                            }
                                            if (found) break
                                        }
                                        found
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
            val errorBone = if (quizMode == QuizMode.TAP && result != null && !result.wasCorrect) result.selectedOption else null

            BoneImage(
                bone = currentBone,
                highlightColor = highlightColorState.value.copy(alpha = highlightAlphaState.value),
                errorBone = errorBone,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
            )
        }

        Spacer(Modifier.height(8.dp))

        // --- INTERACTION AREA ---
        val interactionAreaHeight = when (quizMode) {
            QuizMode.TAP -> 60.dp
            QuizMode.WRITE -> 120.dp
            QuizMode.CHOOSE -> 220.dp
        }

        Box(modifier = Modifier.fillMaxWidth().height(interactionAreaHeight), contentAlignment = Alignment.TopCenter) {
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
                            label = { Text(stringResource(R.string.quiz_write_label, language.name.lowercase())) },
                            singleLine = true,
                            enabled = !isAnswered,
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                autoCorrectEnabled = false,
                                keyboardType = KeyboardType.Password
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (writtenAnswer.isNotBlank()) {
                                        viewModel.submitWrittenAnswer(writtenAnswer, language)
                                    }
                                }
                            ),
                            colors = if (isAnswered) {
                                val color = if (wasCorrect) CorrectAnswerColor else FalseAnswerColor
                                OutlinedTextFieldDefaults.colors(disabledBorderColor = color, disabledTextColor = color, disabledLabelColor = color)
                            } else { OutlinedTextFieldDefaults.colors() }
                        )
                        if (isAnswered) {
                            Text(
                                text = if (wasCorrect) stringResource(R.string.quiz_correct) else stringResource(R.string.quiz_correct_answer_is, currentBone.getName(language)),
                                color = if (wasCorrect) CorrectAnswerColor else FalseAnswerColor,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
                QuizMode.TAP -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Bone name (always visible)
                        Text(
                            text = currentBone.getName(language),
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                        // Result text (only visible after answering)
                        if (answerResult is AnswerResult.Answered && result != null) {
                            Text(
                                text = if (result.wasCorrect) stringResource(R.string.quiz_correct) else stringResource(R.string.quiz_incorrect_tapped, result.selectedOption?.getName(language) ?: ""),
                                color = if (result.wasCorrect) CorrectAnswerColor else FalseAnswerColor,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        // --- NAVIGATION BUTTON AREA ---
        Box(modifier = Modifier.fillMaxWidth().height(48.dp), contentAlignment = Alignment.Center) {
            if (answerResult is AnswerResult.Answered) {
                Button(onClick = viewModel::advance) {
                    val nextButtonText = if (session.remainingBones.size == 1) stringResource(R.string.quiz_finish) else stringResource(R.string.quiz_next)
                    Text(if (settingsState.enableAutoAdvance) "$nextButtonText ($countdown)" else nextButtonText)
                }
            }
        }
    }
}
