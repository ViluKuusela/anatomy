package com.viluappdev.anatomy.ui.navigation

/**
 * A sealed class that defines the navigation routes in the application.
 */
sealed class Screen(val route: String) {
    data object QuizStart : Screen("quiz_start")

    data object Quiz : Screen("quiz/{anatomyArea}/{language}/{quizMode}") {
        fun createRoute(anatomyArea: String, language: String, quizMode: String) =
            "quiz/$anatomyArea/$language/$quizMode"
    }

    data object Settings : Screen("settings")
}
