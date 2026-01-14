package com.example.anatomy.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anatomy.ui.language.Language

/**
 * This composable function represents the starting screen for the quiz.
 * It allows the user to select the anatomy area, language, and answer type before starting the quiz.
 *
 * @param onStartQuiz A callback function that is invoked when the user clicks the "Start Quiz" button.
 *                    It provides the selected anatomy area, language, and a boolean indicating if it's a multiple-choice quiz.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizStartScreen(
    viewModel: QuizStartViewModel,
    onStartQuiz: (String, Language, Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // List of available anatomy areas for the quiz.
    val anatomyAreas = listOf("Skull", "Upper Body", "Hand", "Lower Body", "Foot")

    // State variables to control the expansion of the dropdown menus.
    var anatomyAreaExpanded by remember { mutableStateOf(false) }
    var languageExpanded by remember { mutableStateOf(false) }

    // The main layout is a Column that centers its children.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Anatomy Quiz!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        // Dropdown menu for selecting the anatomy area.
        ExposedDropdownMenuBox(
            expanded = anatomyAreaExpanded,
            onExpandedChange = { anatomyAreaExpanded = !anatomyAreaExpanded }
        ) {
            OutlinedTextField(
                value = uiState.anatomyArea,
                onValueChange = {},
                readOnly = true,
                label = { Text("Anatomy Area") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = anatomyAreaExpanded)
                },
                modifier = Modifier.menuAnchor() // This is important to anchor the dropdown menu.
            )

            ExposedDropdownMenu(
                expanded = anatomyAreaExpanded,
                onDismissRequest = { anatomyAreaExpanded = false }
            ) {
                anatomyAreas.forEach { area ->
                    DropdownMenuItem(
                        text = { Text(area) },
                        onClick = {
                            viewModel.setAnatomyArea(area)
                            anatomyAreaExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown menu for selecting the language.
        ExposedDropdownMenuBox(
            expanded = languageExpanded,
            onExpandedChange = { languageExpanded = !languageExpanded }
        ) {
            OutlinedTextField(
                value = uiState.language.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Language") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = languageExpanded)
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
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

        Spacer(modifier = Modifier.height(32.dp))

        // Radio buttons for selecting the answer type.
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = uiState.isMultipleChoice,
                onClick = { viewModel.setIsMultipleChoice(true) }
            )
            Text("Multiple Choice")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = !uiState.isMultipleChoice,
                onClick = { viewModel.setIsMultipleChoice(false) }
            )
            Text("Written Answer")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Button to start the quiz with the selected options.
        Button(onClick = { onStartQuiz(uiState.anatomyArea, uiState.language, uiState.isMultipleChoice) }) {
            Text("Start Quiz")
        }
    }
}
