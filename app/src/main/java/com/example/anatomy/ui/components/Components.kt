package com.example.anatomy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.anatomy.data.Bone
import com.example.anatomy.ui.theme.CorrectAnswerColor
import com.example.anatomy.ui.theme.FalseAnswerColor

/**
 * A composable that displays a bone image, dynamically highlighting a specific part.
 * Uses alpha = 0.5f for highlights to keep the underlying bone details visible.
 */
@Composable
fun BoneImage(
    bone: Bone,
    highlightColor: Color,
    errorBone: Bone? = null,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // 1. Draw the base anatomy image.
        Image(
            painter = painterResource(id = bone.baseDrawableRes),
            contentDescription = bone.id,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // 2. Highlight error bone if user made a wrong tap.
        if (errorBone != null) {
            Image(
                painter = painterResource(id = errorBone.highlightMaskRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(FalseAnswerColor),
                alpha = 0.5f,
                contentScale = ContentScale.Fit
            )
        }

        // 3. Highlight the correct/target bone.
        Image(
            painter = painterResource(id = bone.highlightMaskRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(highlightColor),
            alpha = 0.5f,
            contentScale = ContentScale.Fit
        )
    }
}

/**
 * Custom button for quiz options that handles success/error/idle states visually.
 */
@Composable
fun AnswerButton(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val containerColor = when {
        isCorrect -> CorrectAnswerColor
        isSelected && !isCorrect -> FalseAnswerColor
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = when {
        isCorrect || isSelected -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text)
    }
}
