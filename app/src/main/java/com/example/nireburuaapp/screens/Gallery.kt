package com.example.nireburuaapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nireburuaapp.R


data class Photo(val resourceId: Int)

@Composable
fun GalleryScreen() {
    // Lista de fotos utilizando recursos de drawable
    val photos = listOf(
        Photo(R.drawable.photo1),
        Photo(R.drawable.photo2),
        Photo(R.drawable.photo3),
        Photo(R.drawable.photo4),
        Photo(R.drawable.photo5),
        Photo(R.drawable.photo6)
    )
    ResponsivePhotoGallery(photos)
}

@Composable
fun ResponsivePhotoGallery(photos: List<Photo>) {
    // Obtener el ancho de la pantalla
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val columnWidth = 200.dp // Ancho deseado para cada columna
    val columns = (screenWidth / columnWidth).toInt()

    // Crear una cuadrícula vertical perezosa para mostrar las fotos
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Iterar sobre la lista de fotos
        items(photos) { photo ->
            PhotoItem(photo)
        }
    }
}

@Composable
fun PhotoItem(photo: Photo) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .aspectRatio(1f) // Hace que la imagen sea cuadrada
            .clickable { /* Manejar clic en la imagen */ }
    ) {
        Image(
            painter = painterResource(id = photo.resourceId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}