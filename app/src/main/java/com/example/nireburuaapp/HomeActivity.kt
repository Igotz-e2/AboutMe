package com.example.nireburuaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.nireburuaapp.ui.theme.AboutMeTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutMeTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {

}

