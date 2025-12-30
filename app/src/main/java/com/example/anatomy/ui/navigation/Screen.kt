package com.example.anatomy.ui.navigation

/**
 * A sealed class that defines the navigation routes in the application.
 * Using a sealed class for navigation routes provides type safety and allows for easy
 * management of navigation destinations.
 *
 * @param route The navigation route string. This is used by the NavController to identify the screen.
 */
sealed class Screen(val route: String) {
    /**
     * Represents the quiz start screen where the user selects quiz options.
     */
    data object QuizStart : Screen("quiz_start")

    /**
     * Represents the main quiz screen.
     * The route includes placeholders for arguments: {anatomyArea}, {language}, and {isMultipleChoice}.
     */
    data object Quiz : Screen("quiz/{anatomyArea}/{language}/{isMultipleChoice}") {
        /**
         * Creates a navigation route for the Quiz screen with the provided arguments.
         *
         * @param anatomyArea The selected anatomy area.
         * @param language The selected language.
         * @param isMultipleChoice A boolean indicating if the quiz is multiple choice.
         * @return A complete route string with the arguments embedded.
         */
        fun createRoute(anatomyArea: String, language: String, isMultipleChoice: Boolean) =
            "quiz/$anatomyArea/$language/$isMultipleChoice"
    }

    /**
     * Represents the settings screen.
     */
    data object Settings : Screen("settings")
}
