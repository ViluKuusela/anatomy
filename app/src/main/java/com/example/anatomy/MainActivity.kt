package com.example.anatomy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anatomy.ui.navigation.AppNavHost
import com.example.anatomy.ui.settings.SettingsScreen
import com.example.anatomy.ui.settings.SettingsViewModel
import com.example.anatomy.ui.settings.SettingsViewModelFactory
import com.example.anatomy.ui.theme.AnatomyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnatomyTheme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot() {
    AppNavHost()
}

