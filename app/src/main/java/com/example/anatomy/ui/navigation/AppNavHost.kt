package com.example.anatomy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.anatomy.data.settings.QuizMode
import com.example.anatomy.data.settings.SettingsRepository
import com.example.anatomy.ui.language.Language
import com.example.anatomy.ui.quiz.QuizScreen
import com.example.anatomy.ui.quiz.QuizStartScreen
import com.example.anatomy.ui.quiz.QuizStartViewModel
import com.example.anatomy.ui.settings.SettingsScreen
import com.example.anatomy.ui.settings.SettingsViewModel

/**
 * AppNavHost is the main navigation component of the application.
 */
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val settingsRepository = SettingsRepository(context)

    NavHost(
        navController = navController,
        startDestination = Screen.QuizStart.route
    ) {
        composable(Screen.QuizStart.route) {
            val quizStartViewModel: QuizStartViewModel = viewModel {
                QuizStartViewModel(settingsRepository)
            }
            QuizStartScreen(
                viewModel = quizStartViewModel,
                onOpenSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onStartQuiz = { anatomyArea, language, quizMode ->
                    navController.navigate(
                        Screen.Quiz.createRoute(anatomyArea, language.name, quizMode.name)
                    )
                }
            )
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument("anatomyArea") { type = NavType.StringType },
                navArgument("language") { type = NavType.StringType },
                navArgument("quizMode") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val anatomyArea = backStackEntry.arguments?.getString("anatomyArea") ?: "Hand"
            val language = backStackEntry.arguments?.getString("language")?.let { Language.valueOf(it) } ?: Language.LATIN
            val quizModeString = backStackEntry.arguments?.getString("quizMode") ?: QuizMode.CHOOSE.name
            val quizMode = QuizMode.valueOf(quizModeString)
            
            val settingsViewModel: SettingsViewModel = viewModel {
                SettingsViewModel(settingsRepository)
            }

            QuizScreen(
                anatomyArea = anatomyArea,
                language = language,
                quizMode = quizMode,
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
