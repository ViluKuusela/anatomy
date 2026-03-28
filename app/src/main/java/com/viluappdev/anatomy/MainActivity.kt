package com.viluappdev.anatomy

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.viluappdev.anatomy.data.settings.QuizStartUiState
import com.viluappdev.anatomy.data.settings.SettingsRepository
import com.viluappdev.anatomy.ui.language.Language
import com.viluappdev.anatomy.ui.navigation.AppNavHost
import com.viluappdev.anatomy.ui.theme.AnatomyTheme
import kotlinx.coroutines.flow.combine
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install SplashScreen BEFORE calling super.onCreate()
        val splashScreen = installSplashScreen()
        
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settingsRepository = SettingsRepository(this)

        // Combine both general and quiz settings to wait for all DataStore reads
        val appReadyFlow = combine(
            settingsRepository.settingsFlow,
            settingsRepository.quizStartUiState
        ) { settings, quizStart ->
            Pair(settings, quizStart)
        }

        setContent {
            // Observe combined settings flow
            val appState by appReadyFlow.collectAsState(initial = null)
            
            // Keep the splash screen visible until appState is loaded
            splashScreen.setKeepOnScreenCondition {
                appState == null
            }

            if (appState == null) return@setContent

            val (settingsData, quizStartInitialState) = appState!!

            // Determine the locale based on user preference or system default
            val locale = remember(settingsData.uiLanguage) {
                when (settingsData.uiLanguage) {
                    Language.FINNISH -> Locale.forLanguageTag("fi")
                    Language.ENGLISH -> Locale.forLanguageTag("en")
                    else -> Locale.getDefault()
                }
            }
            
            // Create a localized context that overrides the resources for stringResource calls
            val context = LocalContext.current
            val localizedContext = remember(context, locale) {
                val configuration = Configuration(context.resources.configuration)
                configuration.setLocale(locale)
                context.createConfigurationContext(configuration)
            }

            // Provide the localized context to the entire Compose tree
            CompositionLocalProvider(LocalContext provides localizedContext) {
                AnatomyTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppRoot(quizStartInitialState)
                    }
                }
            }
        }
    }
}

@Composable
fun AppRoot(quizStartInitialState: QuizStartUiState) {
    AppNavHost(quizStartInitialState)
}
