package com.example.anatomy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.example.anatomy.data.Bone
import com.example.anatomy.ui.theme.CorrectAnswerColor
import com.example.anatomy.ui.theme.FalseAnswerColor

/**
 * A composable that displays a bone image, dynamically highlighting a specific part.
 * It layers a base image with a highlight mask, which is tinted with the provided color.
 *
 * @param bone The bone to display, containing the base and mask drawable resources.
 * @param highlightColor The color to use for highlighting the specific bone part.
 * @param modifier Modifier for this composable.
 */
@Composable
fun BoneImage(
    bone: Bone,
    highlightColor: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // Draw the base image (e.g., the full hand).
        Image(
            painter = painterResource(id = bone.baseDrawableRes),
            contentDescription = bone.id,
            modifier = Modifier.fillMaxWidth()
        )

        // Layer the highlight mask on top and tint it with the highlight color.
        Image(
            painter = painterResource(id = bone.highlightMaskRes),
            contentDescription = null, // Decorative
            modifier = Modifier.fillMaxWidth(),
            colorFilter = ColorFilter.tint(highlightColor),
            alpha = 0.5f
        )
    }
}

@Composable
fun AnswerButton(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val colors = ButtonDefaults.buttonColors(
        disabledContainerColor = when {
            isSelected && !isCorrect -> FalseAnswerColor
            isCorrect -> CorrectAnswerColor
            else -> MaterialTheme.colorScheme.surfaceVariant
        },
        disabledContentColor = when {
            isSelected -> MaterialTheme.colorScheme.onPrimary
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        }
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text)
    }
}
