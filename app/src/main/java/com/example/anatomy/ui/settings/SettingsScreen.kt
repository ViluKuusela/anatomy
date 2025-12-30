package com.example.anatomy.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anatomy.data.settings.AnswerMode
import com.example.anatomy.ui.language.Language

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val settings by viewModel.uiState.collectAsState()


    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(32.dp))

        // Auto-advance switch
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Enable Auto-Advance", modifier = Modifier.weight(1f))
            Switch(
                checked = settings.enableAutoAdvance,
                onCheckedChange = { viewModel.setAutoAdvanceEnabled(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Auto-next delay slider
        Column {
            Text("Auto Next Delay: ${settings.autoNextDelaySeconds} sec")
            Slider(
                value = settings.autoNextDelaySeconds.toFloat(),
                onValueChange = { viewModel.setAutoNextDelay(it.toInt()) },
                valueRange = 1f..10f,
                steps = 9,
                enabled = settings.enableAutoAdvance
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

