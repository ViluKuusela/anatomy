package com.example.anatomy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.anatomy.ui.language.Language
import com.example.anatomy.ui.quiz.QuizScreen
import com.example.anatomy.ui.quiz.QuizStartScreen
import com.example.anatomy.ui.settings.SettingsScreen
import com.example.anatomy.ui.settings.SettingsViewModel
import com.example.anatomy.ui.settings.SettingsViewModelFactory

/**
 * AppNavHost is the main navigation component of the application.
 * It uses Jetpack Navigation Compose to define the navigation graph.
 */
@Composable
fun AppNavHost() {
    // Create a NavController to handle navigation events.
    val navController = rememberNavController()
    // Get the current context, required for the ViewModel factory.
    val context = LocalContext.current

    // Obtain an instance of SettingsViewModel, scoped to the navigation graph.
    // A custom factory is used to pass the context to the ViewModel.
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(context)
    )

    // NavHost defines the navigation graph.
    NavHost(
        navController = navController,
        startDestination = Screen.QuizStart.route // The first screen to be shown.
    ) {
        // Define the "Quiz Start" screen destination.
        composable(Screen.QuizStart.route) {
            // Display the QuizStartScreen composable.
            // It has a callback that is triggered when the user wants to start the quiz.
            QuizStartScreen { anatomyArea, language, isMultipleChoice ->
                // When the quiz is started, navigate to the main quiz screen.
                // We pass the user's selections (anatomy area, language, and answer type)
                // as arguments via the navigation route.
                navController.navigate(
                    Screen.Quiz.createRoute(anatomyArea, language.name, isMultipleChoice)
                )
            }
        }

        // Define the "Quiz" screen destination.
        // This route includes arguments for anatomyArea, language, and isMultipleChoice.
        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument("anatomyArea") { type = NavType.StringType },
                navArgument("language") { type = NavType.StringType },
                navArgument("isMultipleChoice") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            // Extract the arguments from the NavBackStackEntry.
            // Provide default values in case the arguments are not present.
            val anatomyArea = backStackEntry.arguments?.getString("anatomyArea") ?: "Hand"
            val language = backStackEntry.arguments?.getString("language")?.let { Language.valueOf(it) } ?: Language.LATIN

            // Display the QuizScreen composable with the extracted arguments.
            QuizScreen(
                anatomyArea = anatomyArea,
                language = language,
                settingsViewModel = settingsViewModel,
                onOpenSettings = {
                    // Provide a lambda to navigate to the Settings screen.
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        // Define the "Settings" screen destination.
        composable(Screen.Settings.route) {
            // Display the SettingsScreen composable.
            SettingsScreen(
                viewModel = settingsViewModel,
                onBack = {
                    // Provide a lambda to navigate back to the previous screen.
                    navController.popBackStack()
                }
            )
        }
    }
}
