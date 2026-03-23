package com.example.anatomy.ui.settings

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anatomy.R
import com.example.anatomy.ui.language.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showCredits by remember { mutableStateOf(false) }
    var languageExpanded by remember { mutableStateOf(false) }

    if (showCredits) {
        AlertDialog(
            onDismissRequest = { showCredits = false },
            title = { Text(stringResource(R.string.settings_credits_title)) },
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
                TextButton(onClick = { showCredits = false }) { Text(stringResource(R.string.settings_close)) }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.cd_back))
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
            // App UI Language Selection
            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.settings_ui_language))
                Box {
                    TextButton(onClick = { languageExpanded = true }) {
                        Text(if (uiState.uiLanguage == Language.FINNISH) "Suomi" else "English")
                    }
                    DropdownMenu(
                        expanded = languageExpanded,
                        onDismissRequest = { languageExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("English") },
                            onClick = {
                                viewModel.setUiLanguage(Language.ENGLISH)
                                languageExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Suomi") },
                            onClick = {
                                viewModel.setUiLanguage(Language.FINNISH)
                                languageExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Auto-advance setting
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.settings_auto_advance), fontWeight = FontWeight.Medium)
                Switch(
                    checked = uiState.enableAutoAdvance,
                    onCheckedChange = { viewModel.setAutoAdvanceEnabled(it) }
                )
            }

            // Auto-next delay setting (nested/dependent look)
            val alpha by animateFloatAsState(if (uiState.enableAutoAdvance) 1f else 0.5f, label = "alpha")
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp)
                    .alpha(alpha)
            ) {
                Text(
                    text = stringResource(R.string.settings_auto_next_delay, uiState.autoNextDelaySeconds),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (uiState.enableAutoAdvance) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Slider(
                    value = uiState.autoNextDelaySeconds.toFloat(),
                    onValueChange = { viewModel.setAutoNextDelay(it.toInt()) },
                    valueRange = 1f..10f,
                    steps = 8,
                    enabled = uiState.enableAutoAdvance,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Credits Button
            OutlinedButton(
                onClick = { showCredits = true },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(12.dp)
            ) {
                Icon(Icons.Default.Info, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.settings_credits_button))
            }
        }
    }
}

@Composable
fun CreditItem(title: String, author: String, license: String, source: String, notes: String? = null) {
    Column {
        Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
        Text(text = stringResource(R.string.credit_author, author), style = MaterialTheme.typography.bodyMedium)
        Text(text = stringResource(R.string.credit_license, license), style = MaterialTheme.typography.bodyMedium)
        Text(text = stringResource(R.string.credit_source, source), fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
        notes?.let {
            Text(text = stringResource(R.string.credit_notes, it), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 2.dp))
        }
    }
}
