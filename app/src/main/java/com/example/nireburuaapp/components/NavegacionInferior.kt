package com.example.nireburuaapp.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.nireburuaapp.model.Items_bottom_nav.*
import com.example.nireburuaapp.navigation.currentRoute

@Composable
fun NavegacionInferior(
    navController: NavHostController
){
    val menu_items = listOf(
        Item_bottom_nav1,
        Item_bottom_nav2,
        Item_bottom_nav3,
        Item_bottom_nav4
    )
    BottomAppBar{
        NavigationBar (
            containerColor = MaterialTheme.colorScheme.primary
        ){
            menu_items.forEach{item ->
                val selected = currentRoute(navController)== item.ruta
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(item.ruta)
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }
        }

    }
}