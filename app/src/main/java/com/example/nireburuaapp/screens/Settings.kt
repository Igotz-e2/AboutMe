package com.example.nireburuaapp.screens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nireburuaapp.R
import com.example.nireburuaapp.model.SettingsViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SettingsScreen(viewModel: SettingsViewModel){
    val context = LocalContext.current

    val user: FirebaseUser? by viewModel.user.collectAsState()
    val error: Int? by viewModel.errorMsg.observeAsState()

    if (user != null) {
        // Si el usuario está logueado, mostramos su perfil
        PerfilUsuario (
            user = user!!, onLogout = { viewModel.logout(context) },
        )
    } else {
        // Si no está logueado, mostramos la pantalla de autenticación (login/registro)
        AuthScreen(
            onLogin = { email, password -> viewModel.login(email, password) },
            onRegister = { name, surname, email, password ->
                viewModel.register(
                    name,
                    surname,
                    email,
                    password
                )
            },
            viewModel = viewModel
        )
    }
}

@Composable
fun PerfilUsuario(
    user: FirebaseUser,
    onLogout: () -> Unit
) {
    val userData = remember { mutableStateOf<Map<String, Any>?>(null) }
    val userId = user.uid

    fun obtenerDatosUsuario() {
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    userData.value = document.data
                } else {

                }
            }
    }

    LaunchedEffect(userId) {
        obtenerDatosUsuario()
    }
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(4.dp))

        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Perfil de contacto",
            modifier = Modifier
                .size(150.dp)
                .padding(4.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = stringResource(R.string.titulo_perfil),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar los datos del usuario si están disponibles
        if (userData.value != null) {
            val nombre = userData.value?.get("name") as? String ?: R.string.no_disponible
            val surname = userData.value?.get("surname") as? String ?: R.string.no_disponible
            val email = userData.value?.get("email") as? String ?: R.string.no_disponible

            Text(
                text = stringResource(id = R.string.campo_nombre) + " $nombre",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(id = R.string.campo_apellido) + " $surname",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(id = R.string.campo_email) + " $email",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.boton_cerrar_sesion),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
@Composable
fun AuthScreen(
    onLogin: (String, String) -> Unit,
    onRegister: (String, String, String, String) -> Unit,
    viewModel: SettingsViewModel
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Maneja el resultado de la actividad de inicio de sesión de Google
                viewModel.manejarResultadoInicioSesion(result.data)
            }
        }
    )

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginScreen by remember { mutableStateOf(true) }
    val resources = context.resources


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(if (isLoginScreen) R.string.titulo_iniciar_sesion else R.string.titulo_registrarse),
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(if (isLoginScreen) R.string.instruccion_iniciar_sesion else R.string.instruccion_registrarse),
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!isLoginScreen) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.placeholder_nombre)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text(stringResource(R.string.placeholder_apellido)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        TextField(
            value = email.trim(),
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.placeholder_email)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password.trim(),
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.placeholder_contrasena)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                if (isLoginScreen) onLogin(email, password)
                else onRegister(name, surname, email, password)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(if (isLoginScreen) R.string.boton_iniciar_sesion else R.string.boton_registrarse),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                // Inicia la sesión con Google
                viewModel.iniciarSesionConGoogle(context, launcher, context)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icono del logo de Google
                Icon(
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = resources.getString(R.string.contdesc_general),
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                    // Tamaño del logo
                )
                Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el logo y el texto
                Text(
                    text = stringResource(R.string.iniciar_sesion_google),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { isLoginScreen = !isLoginScreen }) {
            Text(
                text = stringResource(if (isLoginScreen) R.string.texto_crear_cuenta else R.string.texto_iniciar_sesion),
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}
