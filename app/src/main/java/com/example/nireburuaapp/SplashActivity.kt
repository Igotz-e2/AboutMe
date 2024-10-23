package com.example.nireburuaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nireburuaapp.screens.SplashScreen
import com.example.nireburuaapp.ui.theme.AboutMeTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AboutMeTheme {
                SplashScreen()
            }
        }
    }
}