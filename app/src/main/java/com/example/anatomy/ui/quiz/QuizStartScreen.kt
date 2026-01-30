package com.example.anatomy.ui.quiz

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.anatomy.R
import com.example.anatomy.data.settings.QuizMode
import com.example.anatomy.ui.language.Language
import androidx.core.graphics.get
import androidx.core.graphics.createBitmap

/**
 * The entry screen of the app where the user selects an anatomy area and quiz settings.
 */
@Composable
fun QuizStartScreen(
    viewModel: QuizStartViewModel,
    onOpenSettings: () -> Unit,
    onStartQuiz: (String, Language, QuizMode) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var languageExpanded by remember { mutableStateOf(false) }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    // Pre-calculate bitmaps for pixel-perfect hit testing on the full skeleton map
    val areaMasks = remember {
        listOf(
            "Hand" to R.drawable.all_bones_hands,
            "Skull" to R.drawable.all_bones_skull,
            "Foot" to R.drawable.all_bones_feet,
            "Upper Body" to R.drawable.all_bones_upper,
            "Lower Body" to R.drawable.all_bones_lower
        ).map { (name, resId) ->
            val drawable = ContextCompat.getDrawable(context, resId)!!
            val bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            name to bitmap
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- HEADER ROW (Settings + Title) ---
        Box(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onOpenSettings,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
            
            Text(
                text = "Anatomy Quiz",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Text("Tap a region to start", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        // --- INTERACTIVE SKELETON MAP ---
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .onGloballyPositioned { boxSize = it.size }
                .pointerInput(uiState) {
                    detectTapGestures { offset ->
                        if (boxSize.width > 0 && boxSize.height > 0) {
                            val normalizedX = offset.x / boxSize.width
                            val normalizedY = offset.y / boxSize.height
                            
                            val hitArea = areaMasks.firstOrNull { (_, bitmap) ->
                                val centerX = (normalizedX * bitmap.width).toInt()
                                val centerY = (normalizedY * bitmap.height).toInt()
                                
                                var isHit = false
                                val radius = 4
                                for (dx in -radius..radius) {
                                    for (dy in -radius..radius) {
                                        val x = (centerX + dx).coerceIn(0, bitmap.width - 1)
                                        val y = (centerY + dy).coerceIn(0, bitmap.height - 1)
                                        if (android.graphics.Color.alpha(bitmap[x, y]) > 0) {
                                            isHit = true
                                            break
                                        }
                                    }
                                    if (isHit) break
                                }
                                isHit
                            }?.first

                            hitArea?.let { area ->
                                viewModel.setAnatomyArea(area)
                                onStartQuiz(area, uiState.language, uiState.quizMode)
                            }
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.all_bones_base),
                contentDescription = "Full skeleton selector map",
                modifier = Modifier.fillMaxHeight()
            )
            
            ImageMask(R.drawable.all_bones_skull, Color(0x902196F3))
            ImageMask(R.drawable.all_bones_upper, Color(0x9C4CAF50))
            ImageMask(R.drawable.all_bones_hands,  Color(0x6F9C27B0))
            ImageMask(R.drawable.all_bones_lower, Color(0x4BBDAD2D))
            ImageMask(R.drawable.all_bones_feet,  Color(0x57CE3C31))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- SETTINGS PANEL ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Quiz Mode Selection Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().height(40.dp)
                ) {
                    Text(
                        text = "Type:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        QuizMode.values().forEach { mode ->
                            RadioButton(
                                selected = uiState.quizMode == mode,
                                onClick = { viewModel.setQuizMode(mode) },
                                modifier = Modifier.size(32.dp)
                            )
                            val label = when(mode) {
                                QuizMode.TAP -> "Tap"
                                QuizMode.CHOOSE -> "Choose"
                                QuizMode.WRITE -> "Write"
                            }
                            Text(
                                text = label, 
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 2.dp, end = 24.dp)
                            )
                        }
                    }
                }

                // Language Selection Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().height(40.dp)
                ) {
                    Text(
                        text = "Language:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(85.dp)
                    )
                    Box {
                        TextButton(
                            onClick = { languageExpanded = true },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                            modifier = Modifier.heightIn(min = 32.dp)
                        ) {
                            Text(uiState.language.name)
                        }
                        DropdownMenu(
                            expanded = languageExpanded,
                            onDismissRequest = { languageExpanded = false }
                        ) {
                            Language.values().forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text(lang.name) },
                                    onClick = {
                                        viewModel.setLanguage(lang)
                                        languageExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageMask(drawableId: Int, tintColor: Color) {
    Image(
        painter = painterResource(id = drawableId),
        contentDescription = null,
        modifier = Modifier.fillMaxHeight(),
        colorFilter = ColorFilter.tint(tintColor)
    )
}
