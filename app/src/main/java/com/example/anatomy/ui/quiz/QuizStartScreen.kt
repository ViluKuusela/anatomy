package com.example.anatomy.ui.quiz

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import com.example.anatomy.ui.language.Language
import androidx.core.graphics.get
import androidx.core.graphics.createBitmap

@Composable
fun QuizStartScreen(
    viewModel: QuizStartViewModel,
    onStartQuiz: (String, Language, Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var languageExpanded by remember { mutableStateOf(false) }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    // Pre-calculate bitmaps for pixel-perfect hit testing
    val areaMasks = remember {
        listOf(
            "Hand" to R.drawable.all_bones_hand,
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Anatomy Quiz", style = MaterialTheme.typography.headlineLarge)
        Text("Tap a region to start", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

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
                                val x = (normalizedX * bitmap.width).toInt().coerceIn(0, bitmap.width - 1)
                                val y = (normalizedY * bitmap.height).toInt().coerceIn(0, bitmap.height - 1)
                                val pixel = bitmap[x, y]
                                android.graphics.Color.alpha(pixel) > 0
                            }?.first

                            hitArea?.let { area ->
                                viewModel.setAnatomyArea(area)
                                onStartQuiz(area, uiState.language, uiState.isMultipleChoice)
                            }
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.all_bones_base),
                contentDescription = "Full skeleton",
                modifier = Modifier.fillMaxHeight()
            )
            
            ImageMask(R.drawable.all_bones_skull, Color(0x662196F3))
            ImageMask(R.drawable.all_bones_upper, Color(0x66FF5722))
            ImageMask(R.drawable.all_bones_hand,  Color(0x66FFEB3B))
            ImageMask(R.drawable.all_bones_lower, Color(0x664CAF50))
            ImageMask(R.drawable.all_bones_feet,  Color(0x669C27B0))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Settings Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Language Selection: All grouped tightly to the left
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Language:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(90.dp)
                    )
                    Box {
                        TextButton(onClick = { languageExpanded = true }) {
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

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Answer Type Selection: All grouped tightly to the left
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Type:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(90.dp)
                    )
                    RadioButton(
                        selected = uiState.isMultipleChoice,
                        onClick = { viewModel.setIsMultipleChoice(true) }
                    )
                    Text("Multiple Choice", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    RadioButton(
                        selected = !uiState.isMultipleChoice,
                        onClick = { viewModel.setIsMultipleChoice(false) }
                    )
                    Text("Written", style = MaterialTheme.typography.bodyMedium)
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
