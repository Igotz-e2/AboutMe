package com.example.nireburuaapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nireburuaapp.model.ContadorViewModel

@Composable
fun Contador(contadorViewModel: ContadorViewModel) {
    // Observar el valor del contador desde el ViewModel
    val contador = contadorViewModel.contador.observeAsState(0)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Contador: ${contador.value}",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { contadorViewModel.incrementar() }) {
                Text("Incrementar")
            }

            Button(onClick = { contadorViewModel.decrementar() }) {
                Text("Decrementar")
            }
        }
        Column {
            Button(onClick = { contadorViewModel.resetear() }) {
                Text("Resetear")
            }
        }
    }
}