package com.example.anatomy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.anatomy.data.settings.SettingsRepository
import com.example.anatomy.ui.language.Language
import com.example.anatomy.ui.quiz.QuizScreen
import com.example.anatomy.ui.quiz.QuizStartScreen
import com.example.anatomy.ui.quiz.QuizStartViewModel
import com.example.anatomy.ui.settings.SettingsScreen
import com.example.anatomy.ui.settings.SettingsViewModel

/**
 * AppNavHost is the main navigation component of the application.
 * It uses Jetpack Navigation Compose to define the navigation graph.
 */
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Create a single instance of the repository that will be shared across all ViewModels.
    val settingsRepository = SettingsRepository(context)

    NavHost(
        navController = navController,
        startDestination = Screen.QuizStart.route
    ) {
        composable(Screen.QuizStart.route) {
            // Create QuizStartViewModel using the initializer block, which is the modern way.
            val quizStartViewModel: QuizStartViewModel = viewModel {
                QuizStartViewModel(settingsRepository)
            }
            QuizStartScreen(quizStartViewModel) { anatomyArea, language, isMultipleChoice ->
                navController.navigate(
                    Screen.Quiz.createRoute(anatomyArea, language.name, isMultipleChoice)
                )
            }
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument("anatomyArea") { type = NavType.StringType },
                navArgument("language") { type = NavType.StringType },
                navArgument("isMultipleChoice") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val anatomyArea = backStackEntry.arguments?.getString("anatomyArea") ?: "Hand"
            val language = backStackEntry.arguments?.getString("language")?.let { Language.valueOf(it) } ?: Language.LATIN
            val isMultipleChoice = backStackEntry.arguments?.getBoolean("isMultipleChoice") ?: true
            // Create SettingsViewModel using the initializer block.
            val settingsViewModel: SettingsViewModel = viewModel {
                SettingsViewModel(settingsRepository)
            }

            QuizScreen(
                anatomyArea = anatomyArea,
                language = language,
                isMultipleChoice = isMultipleChoice,
                settingsViewModel = settingsViewModel,
                onOpenSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onFinish = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Settings.route) {
            // Re-use the same ViewModel creation pattern.
            val settingsViewModel: SettingsViewModel = viewModel {
                SettingsViewModel(settingsRepository)
            }
            SettingsScreen(
                viewModel = settingsViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
