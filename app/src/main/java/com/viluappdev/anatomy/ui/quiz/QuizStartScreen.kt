package com.viluappdev.anatomy.ui.quiz

import android.graphics.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import com.viluappdev.anatomy.data.settings.QuizMode
import com.viluappdev.anatomy.ui.language.Language
import com.viluappdev.anatomy.R
import androidx.core.graphics.get

/**
 * The modernized entry screen of the app.
 * Optimized for mobile screens with improved Dark Mode details and combined UI controls.
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
    val isDark = isSystemInDarkTheme()

    // Dynamic professional palette
    val primaryColor = if (isDark) Color(0xFF9ECAFF).copy(alpha = 0.9f) else Color(0xFF0061A4)
    val textColor = if (isDark) Color(0xFFB0BEC5) else Color.DarkGray

    val backgroundBrush = if (isDark) {
        Brush.verticalGradient(colors = listOf(Color(0xFF001529), Color(0xFF121212)))
    } else {
        Brush.verticalGradient(colors = listOf(Color(0xFFE3F2FD), Color(0xFFF5F5F5)))
    }

    @Composable
    fun Language.getDisplayName(): String {
        return when (this) {
            Language.LATIN -> stringResource(R.string.lang_latin)
            Language.ENGLISH -> stringResource(R.string.lang_english)
            Language.FINNISH -> stringResource(R.string.lang_finnish)
        }
    }

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

    Box(modifier = Modifier.fillMaxSize().background(backgroundBrush)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 16.dp, vertical = 0.dp),
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
                        tint = primaryColor
                    )
                }

                Text(
                    text = stringResource(R.string.quiz_start_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
            }

            Text(
                text = stringResource(R.string.quiz_start_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- INTERACTIVE SKELETON MAP ---
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clipToBounds()
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
                // Background glow (enhanced visibility in dark mode)
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .blur(50.dp)
                        .background(
                            Brush.radialGradient(
                                colors = if (isDark) {
                                    listOf(primaryColor.copy(alpha = 0.4f), Color.Transparent)
                                } else {
                                    listOf(Color(0xFFBBDEFB), Color.Transparent)
                                }
                            ),
                            shape = CircleShape
                        )
                )

                Image(
                    painter = painterResource(id = R.drawable.all_bones_base),
                    contentDescription = stringResource(R.string.cd_skeleton_map),
                    modifier = Modifier.fillMaxHeight(),
                )

                // Manual color regions (preserved)
                ImageMask(R.drawable.all_bones_skull, Color(0x800091C8))
                ImageMask(R.drawable.all_bones_upper, Color(0x804CE1C3))
                ImageMask(R.drawable.all_bones_hands, Color(0x7300AFAF))
                ImageMask(R.drawable.all_bones_lower, Color(0x8012BE7D))
                ImageMask(R.drawable.all_bones_feet,  Color(0x8000BCD4))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- INTEGRATED SETTINGS PANEL ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Quiz Mode Selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
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
                                    text = label.uppercase(),
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = primaryColor,
                                selectedBorderColor = primaryColor,
                                borderWidth = 1.dp,
                                selectedBorderWidth = 2.5.dp
                            ),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color.Transparent,
                                labelColor = primaryColor,
                                selectedContainerColor = primaryColor,
                                selectedLabelColor = if (isDark) Color.Black else Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Combined Language Selection Button
                val langNames = Language.entries.associateWith { lang ->
                    when(lang) {
                        Language.LATIN -> stringResource(R.string.lang_latin)
                        Language.ENGLISH -> stringResource(R.string.lang_english)
                        Language.FINNISH -> stringResource(R.string.lang_finnish)
                    }.uppercase()
                }

                Box(contentAlignment = Alignment.Center) {
                    val currentLangName = langNames[uiState.language] ?: ""

                    OutlinedButton(
                        onClick = { languageExpanded = true },
                        modifier = Modifier.wrapContentWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, primaryColor),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryColor)
                    ) {
                        val label = stringResource(R.string.label_anatomy_language).replace("\n", " ")
                        Text(
                            text = "$label $currentLangName",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )
                    }

                    Box(modifier = Modifier.align(Alignment.Center)) {
                        DropdownMenu(
                            expanded = languageExpanded,
                            onDismissRequest = { languageExpanded = false },
                            offset = DpOffset(x = 0.dp, y = 4.dp),
                            modifier = Modifier.background(if (isDark) Color(0xFF1A1C1E) else Color.White)
                        ) {
                            Language.entries.forEach { lang ->
                                val langName = langNames[lang] ?: ""
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = langName,
                                            color = primaryColor,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Start
                                        ) 
                                    },
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
