package com.example.anatomy.ui.quiz

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import com.example.anatomy.R
import com.example.anatomy.data.settings.QuizMode
import com.example.anatomy.ui.language.Language

/**
 * The modernized entry screen of the app.
 * Integrated UI elements that blend into the background for a clean, unified look.
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

    // Helper to get localized name for the Language enum
    @Composable
    fun Language.getDisplayName(): String {
        return when (this) {
            Language.LATIN -> stringResource(R.string.lang_latin)
            Language.ENGLISH -> stringResource(R.string.lang_english)
            Language.FINNISH -> stringResource(R.string.lang_finnish)
        }
    }

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

    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(
        colors = listOf(Color(0xFFE3F2FD), Color(0xFFF5F5F5)) 
    ))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 20.dp, vertical = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- HEADER ---
            Box(
                modifier = Modifier.fillMaxWidth().height(64.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onOpenSettings,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = stringResource(R.string.cd_settings),
                        tint = Color(0xFF0061A4)
                    )
                }

                Text(
                    text = stringResource(R.string.quiz_start_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0061A4)
                )
            }

            Text(
                text = stringResource(R.string.quiz_start_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                                            // Changed from bitmap[x, y] to bitmap.getPixel(x, y) to fix compile error
                                            if (android.graphics.Color.alpha(bitmap.getPixel(x, y)) > 0) {
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
                // Subtle glow behind skeleton
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .blur(50.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0xFFBBDEFB), Color.Transparent)
                            ),
                            shape = CircleShape
                        )
                )

                // The skeleton base image
                Image(
                    painter = painterResource(id = R.drawable.all_bones_base),
                    contentDescription = stringResource(R.string.cd_skeleton_map),
                    modifier = Modifier.fillMaxHeight()
                )

                // Refined Harmonic Regions
                ImageMask(R.drawable.all_bones_skull, Color(0x800091C8))
                ImageMask(R.drawable.all_bones_upper, Color(0x8000C87D))
                ImageMask(R.drawable.all_bones_hands, Color(0x8000BCD4))
                ImageMask(R.drawable.all_bones_lower, Color(0x804CE1C3))
                ImageMask(R.drawable.all_bones_feet,  Color(0x7300AFAF))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- INTEGRATED SETTINGS PANEL ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                // Quiz Mode Selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text(
                        text = stringResource(R.string.label_type),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF0061A4),
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        QuizMode.entries.forEach { mode ->
                            val isSelected = uiState.quizMode == mode
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.setQuizMode(mode) },
                                label = {
                                    val label = when(mode) {
                                        QuizMode.TAP -> stringResource(R.string.quiz_mode_tap)
                                        QuizMode.CHOOSE -> stringResource(R.string.quiz_mode_choose)
                                        QuizMode.WRITE -> stringResource(R.string.quiz_mode_write)
                                    }
                                    Text(
                                        text = label, 
                                        fontSize = 12.sp, 
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = Color(0xFF0061A4),
                                    selectedBorderColor = Color(0xFF0061A4),
                                    borderWidth = 1.5.dp,
                                    selectedBorderWidth = 3.dp
                                ),
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = Color.Transparent,
                                    labelColor = Color(0xFF0061A4),
                                    selectedContainerColor = Color(0xFF0061A4),
                                    selectedLabelColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Language Selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text(
                        text = stringResource(R.string.label_anatomy_language),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF0061A4),
                        modifier = Modifier.width(150.dp)
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { languageExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(2.dp, Color(0xFF0061A4)),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                        ) {
                            Text(
                                uiState.language.getDisplayName(),
                                color = Color(0xFF0061A4),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        DropdownMenu(
                            expanded = languageExpanded,
                            onDismissRequest = { languageExpanded = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            Language.entries.forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text(lang.getDisplayName(), color = Color(0xFF0061A4)) },
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
