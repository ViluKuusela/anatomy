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
import androidx.compose.ui.res.painterResource
import com.example.anatomy.data.Bone
import com.example.anatomy.ui.theme.CorrectAnswerColor
import com.example.anatomy.ui.theme.FalseAnswerColor

@Composable
fun BoneImage(
    bone: Bone,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = bone.highlightDrawableRes),
            contentDescription = bone.id,
            modifier = Modifier.fillMaxWidth()
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
