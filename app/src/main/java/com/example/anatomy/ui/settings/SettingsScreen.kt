package com.example.anatomy.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showCredits by remember { mutableStateOf(false) }

    if (showCredits) {
        AlertDialog(
            onDismissRequest = { showCredits = false },
            title = { Text("Image Credits") },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 8.dp)
                ) {
                    CreditItem(
                        title = "Human Skeleton",
                        author = "LadyofHats Mariana Ruiz Villarreal",
                        license = "Public Domain",
                        source = "https://commons.wikimedia.org/wiki/File:Human_skeleton_front_en.svg"
                    )
                    Spacer(Modifier.height(16.dp))
                    CreditItem(
                        title = "Human Skull",
                        author = "LadyofHats Mariana Ruiz Villarreal",
                        license = "Public Domain",
                        source = "https://commons.wikimedia.org/wiki/File:Human_skull_no_text_no_color.svg"
                    )
                    Spacer(Modifier.height(16.dp))
                    CreditItem(
                        title = "Bones of the Hand",
                        author = "DataBase Center for Life Science (DBCLS)",
                        license = "CC BY 4.0",
                        source = "https://commons.wikimedia.org/wiki/File:202110_Anterior_view_of_bones_of_right_hand.svg",
                        notes = "Converted to vector and modified for highlight masks."
                    )
                    Spacer(Modifier.height(16.dp))
                    CreditItem(
                        title = "Bones of the Foot",
                        author = "DataBase Center for Life Science (DBCLS)",
                        license = "CC BY 4.0",
                        source = "https://commons.wikimedia.org/wiki/File:202110_Dorsal_view_of_bones_of_right_foot.svg",
                        notes = "Converted to vector and modified for highlight masks."
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showCredits = false }) { Text("Close") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Auto-advance setting
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Enable auto-advance")
                Switch(
                    checked = uiState.enableAutoAdvance,
                    onCheckedChange = { viewModel.setAutoAdvanceEnabled(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Auto-next delay setting
            Text("Auto-next delay: ${uiState.autoNextDelaySeconds} seconds")
            Slider(
                value = uiState.autoNextDelaySeconds.toFloat(),
                onValueChange = { viewModel.setAutoNextDelay(it.toInt()) },
                valueRange = 1f..10f,
                steps = 8,
                enabled = uiState.enableAutoAdvance
            )

            Spacer(modifier = Modifier.weight(1f))

            // Credits Button
            OutlinedButton(
                onClick = { showCredits = true },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(12.dp)
            ) {
                Icon(Icons.Default.Info, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Image Credits & Licenses")
            }
        }
    }
}

@Composable
fun CreditItem(title: String, author: String, license: String, source: String, notes: String? = null) {
    Column {
        Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
        Text(text = "Author: $author", style = MaterialTheme.typography.bodyMedium)
        Text(text = "License: $license", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Source: $source", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
        notes?.let {
            Text(text = "Notes: $it", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 2.dp))
        }
    }
}
