package com.example.nireburuaapp.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nireburuaapp.MainActivity
import com.example.nireburuaapp.R

@Composable
fun SplashScreen(){
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.profile_picture),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop, // Ajuste para rellenar el círculo
                modifier = Modifier
                    .size(220.dp) // Tamaño de la imagen (círculo)
                    .clip(CircleShape) // Clip para que la imagen sea circular
                    .border(8.dp, MaterialTheme.colorScheme.secondary, CircleShape) // Borde opcional
            )
            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            OutlinedButton(
                onClick = {
                    val intent = Intent(
                        context,
                        MainActivity::class.java
                    )
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary, // Fondo del botón
                    contentColor = MaterialTheme.colorScheme.onSecondary // Texto del botón
                )
            ) {
                Text(
                    text = "Continuar",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
}