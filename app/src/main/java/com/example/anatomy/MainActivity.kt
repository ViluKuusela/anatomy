package com.example.anatomy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.anatomy.ui.quiz.HandBonesQuizScreen
import com.example.anatomy.ui.theme.AnatomyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnatomyTheme {
                HandBonesQuizScreen()
            }
        }
    }
}