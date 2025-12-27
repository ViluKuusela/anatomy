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
    viewModel: SettingsViewModel
) {
    val settings by viewModel.settings.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        // ---------------- Auto-next ----------------
        SettingSection(title = "Auto next") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enable auto advance")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = settings.autoNextEnabled,
                    onCheckedChange = {
                        viewModel.update(
                            settings.copy(autoNextEnabled = it)
                        )
                    }
                )
            }
        }

        // ---------------- Timer seconds ----------------
        SettingSection(title = "Auto next delay (seconds)") {
            Column {
                Slider(
                    value = settings.autoNextSeconds.toFloat(),
                    onValueChange = {
                        viewModel.update(
                            settings.copy(autoNextSeconds = it.toInt())
                        )
                    },
                    valueRange = 1f..10f,
                    steps = 8,
                    enabled = settings.autoNextEnabled
                )
                Text(
                    text = "${settings.autoNextSeconds} seconds",
                    color = if (settings.autoNextEnabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }
                )
            }
        }

        // ---------------- Language ----------------
        SettingSection(title = "Language") {
            Language.values().forEach { language ->
                RadioOption(
                    text = language.name,
                    selected = settings.language == language,
                    onSelect = {
                        viewModel.update(
                            settings.copy(language = language)
                        )
                    }
                )
            }
        }

        // ---------------- Answer mode ----------------
        SettingSection(title = "Answer mode") {
            AnswerMode.values().forEach { mode ->
                RadioOption(
                    text = mode.name.replace('_', ' '),
                    selected = settings.answerMode == mode,
                    onSelect = {
                        viewModel.update(
                            settings.copy(answerMode = mode)
                        )
                    }
                )
            }
        }
    }
}
