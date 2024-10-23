package com.example.nireburuaapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.nireburuaapp.navigation.NavScreen

sealed class Items_bottom_nav(
    val icon : ImageVector,
    val ruta : String
) {
    object Item_bottom_nav1 : Items_bottom_nav(
        Icons.Outlined.Home,
        NavScreen.Home.name
    )
    object Item_bottom_nav2 : Items_bottom_nav(
        Icons.Outlined.Person,
        NavScreen.AboutMe.name
    )
    object Item_bottom_nav3 : Items_bottom_nav(
        Icons.Outlined.Photo,
        NavScreen.Gallery.name
    )
    object Item_bottom_nav4 : Items_bottom_nav(
        Icons.Outlined.Settings,
        NavScreen.Settings.name
    )

}