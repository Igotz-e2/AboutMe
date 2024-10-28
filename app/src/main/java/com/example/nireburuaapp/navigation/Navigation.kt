package com.example.nireburuaapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nireburuaapp.HomeScreen
import com.example.nireburuaapp.MainActivity
import com.example.nireburuaapp.model.SettingsViewModel
import com.example.nireburuaapp.screens.GalleryScreen
import com.example.nireburuaapp.screens.SettingsScreen

@Composable
fun Navigation(
    navController : NavHostController
){
    NavHost (
        navController = navController,
        startDestination = NavScreen.Home.name
    ){
        composable(NavScreen.Home.name){
            MainActivity()
        }
        composable(NavScreen.AboutMe.name){
            com.example.nireburuaapp.screens.AboutMeScreen(
                name = "Igotz",
                surname = "Idigoras",
                description = "Soy un estudiante de un módulo superior, tengo 19 años y soy de Aretxabaleta, Gipuzkoa. Me apasiona la tecnología y siempre estoy buscando nuevos retos.",
                hobbies = listOf(
                    "Ir al Gymnasio" to Icons.Default.Favorite,
                    "Desarrollar aplicaciones móviles" to Icons.Default.Build,
                    "Jugar videojuegos" to Icons.Default.PlayArrow,
                    "Viajar y explorar nuevas culturas" to Icons.Default.Info
                )
            )
        }
        composable(NavScreen.Gallery.name){
            GalleryScreen()
        }
        composable(NavScreen.Settings.name){
            SettingsScreen(viewModel = SettingsViewModel())
        }
    }
}