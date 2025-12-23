package com.example.anatomy.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AnswerButton(
    text: String,
    correctAnswer: String,
    selectedAnswer: String?,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        selectedAnswer == null ->
            MaterialTheme.colorScheme.primary

        text == correctAnswer ->
            Color(0xFF4CAF50) // green

        text == selectedAnswer && text != correctAnswer ->
            Color(0xFFF44336) // red

        else ->
            MaterialTheme.colorScheme.surfaceVariant
    }

    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = selectedAnswer == null,
        colors = ButtonDefaults.buttonColors(disabledContainerColor = backgroundColor)
    ) {
        Log.d("mytag","test $text $backgroundColor")
        Text(text)
    }
}

