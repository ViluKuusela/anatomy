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
 */
@Composable
fun BoneImage(
    bone: Bone,
    highlightColor: Color,
    errorBone: Bone? = null,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // 1. Draw the base image.
        Image(
            painter = painterResource(id = bone.baseDrawableRes),
            contentDescription = bone.id,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // 2. Highlight error bone if present
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

        // 3. Highlight correct bone
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
