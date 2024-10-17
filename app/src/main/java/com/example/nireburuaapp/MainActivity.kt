package com.example.nireburuaapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.nireburuaapp.ui.theme.NireburuaAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NireburuaAppTheme {
                MainScreen(
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    name: String,
    surname: String,
    description: String,
    hobbies: List<Pair<String, ImageVector>>
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Me") },
                actions = {
                    IconButton(onClick = {
                        // Intent para compartir tu perfil
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Echa un vistazo a mi perfil en https://www.github.com/Igotz-e2")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Compartir perfil")
                    }
                }
            )
        },

        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                FloatingActionButton(
                    onClick = {

                    },
                    containerColor = Color.Green, // Color verde para el corazón
                    modifier = Modifier
                        .padding(16.dp) // Espacio alrededor del botón flotante
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorito",
                        tint = Color.White
                    )
                }
            }

        },
        bottomBar = {
            BottomAppBar {
                Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("igotzidigoras@gmail.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Contacto desde la app")
                            putExtra(Intent.EXTRA_TEXT, "Hola, me gustaría contactarte.")
                        }
                        context.startActivity(emailIntent)
                    }) {
                        Icon(Icons.Default.Email, contentDescription = "")
                        Spacer(modifier = Modifier.width(8.dp)
                            )
                        Text("")
                    }
                }
        },
        content = { innerPadding ->
            Greeting(
                name = name,
                surname = surname,
                description = description,
                hobbies = hobbies,
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}

@Composable
fun Greeting(
    name: String,
    surname: String,
    description: String,
    hobbies: List<Pair<String, ImageVector>>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painter = painterResource(R.drawable.profile_picture),
                contentDescription = "Perfil de contacto",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "$name $surname",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp, 0.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = description,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
        item {
            hobbies.forEach { (hobby, icon) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = hobby,
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            SocialMediaLinks(uriHandler = uriHandler)
        }
    }
}

@Composable
fun SocialMediaLinks(uriHandler: UriHandler, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    uriHandler.openUri("https://www.instagram.com/tuperfil")
                }
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_instagram),
                contentDescription = "Instagram",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Instagram",
                color = Color.Blue
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    uriHandler.openUri("https://www.linkedin.com/in/tuperfil")
                }
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_linkedin),
                contentDescription = "LinkedIn",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "LinkedIn",
                color = Color.Blue
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NireburuaAppTheme {
        MainScreen(
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
}