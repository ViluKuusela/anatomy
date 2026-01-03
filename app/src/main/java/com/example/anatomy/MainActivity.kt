package com.example.anatomy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.anatomy.ui.navigation.AppNavHost
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
