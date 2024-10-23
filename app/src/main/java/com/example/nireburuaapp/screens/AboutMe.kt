package com.example.nireburuaapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nireburuaapp.R

@Composable
fun AboutMeScreen(name: String, surname:String, description:String, hobbies: List<Pair<String, ImageVector>>, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    LazyColumn (modifier = Modifier
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
                contentScale = ContentScale.Crop,
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
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        item {
            Text(
                text = " ",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            hobbies.forEach { (hobby, icon) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Start,  // Alinea a la derecha
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
            SocialMediaLinks(
                uriHandler = uriHandler,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


@Composable
fun SocialMediaLinks(uriHandler: UriHandler, modifier: Modifier) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = " ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    uriHandler.openUri("https://www.linkedin.com/in/")
                }
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_linkedin), // Cambia el nombre a tu icono
                contentDescription = "LinkedIn",
                modifier = Modifier.size(24.dp) // Ajusta el tamaño según lo necesites
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "LinkedIn",
                color = MaterialTheme.colorScheme.primary
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    uriHandler.openUri("https://www.instagram.com/")
                }
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_instagram), // Cambia el nombre a tu icono
                contentDescription = "Instagram",
                modifier = Modifier.size(24.dp) // Ajusta el tamaño según lo necesites
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Instagram",
                color = MaterialTheme.colorScheme.primary
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    uriHandler.openUri("https://www.github.com/")
                }
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_github),
                contentDescription = "GitHub",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "GitHub",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}