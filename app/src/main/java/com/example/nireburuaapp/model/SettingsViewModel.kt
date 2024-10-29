package com.example.nireburuaapp.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nireburuaapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit

class SettingsViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var googleSignInClient: GoogleSignInClient

    // Variable para almacenar el estado del usuario
    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> get() = _user

    private val _errorMsg = MutableLiveData<Int?>()
    val errorMsg: LiveData<Int?> = _errorMsg

    init {
        // Verifica el estado de autenticación al iniciar el ViewModel
        _user.value = auth.currentUser
        _errorMsg.value = null
    }

    fun validarContrasena(password: String): Boolean {
        // Verificar que la contraseña cumpla con los requisitos de seguridad
        val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}\$")
        return passwordPattern.matches(password)
    }

    fun validarEmail(email: String): Boolean {
        // Verificar que el email tiene un formato válido
        val emailPattern = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
        return emailPattern.matches(email)
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMsg.value = R.string.error_empty_fields
            return
        }
        if (!validarEmail(email)) {
            _errorMsg.value = R.string.error_invalid_email
            return
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _user.value = auth.currentUser
                _errorMsg.value = null
            } else {
                _errorMsg.value = R.string.error_login
            }
        }
    }

    fun logout(context: Context) {
        auth.signOut()
        _user.value = null
        googleSignInClient = GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
        googleSignInClient.signOut().addOnCompleteListener {
            _user.value = null // Actualiza el estado del usuario en el ViewModel
        }.addOnFailureListener {
        }
        //ActivityCompat.recreate(Activity())
    }

    fun register(name: String, surname: String, email: String, password: String) {
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _errorMsg.value = R.string.error_empty_fields
            return
        }

        if (!validarEmail(email)) {
            _errorMsg.value = R.string.error_invalid_email
            return
        }

        if (!validarContrasena(password)) {
            _errorMsg.value = R.string.error_weak_password
            return
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                val userData = hashMapOf(
                    "name" to name,
                    "surname" to surname,
                    "email" to email
                )
                // Guarda los datos del usuario en Firestore
                if (userId != null) {
                    firestore.collection("users").document(userId).set(userData)
                        .addOnSuccessListener {
                            _user.value = auth.currentUser // Actualiza el estado del usuario
                            _errorMsg.value = null
                        }.addOnFailureListener {
                            _errorMsg.value = R.string.error_registro
                        }
                }
            } else {
                _errorMsg.value = R.string.error_email_exists
            }
        }
    }

    fun iniciarSesionConGoogle(
        activity: Context,
        signInLauncher: ActivityResultLauncher<Intent>,
        context: Context
    ) {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    fun manejarResultadoInicioSesion(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _user.value = auth.currentUser

                        val userId = auth.currentUser?.uid
                        val userData = hashMapOf(
                            "name" to account.displayName,
                            "surname" to account.displayName,
                            "email" to account.email
                        )
                        if (userId != null) {
                            firestore.collection("users").document(userId).get()
                                .addOnSuccessListener { document ->
                                    if (!document.exists()) {
                                        // Si el documento no existe, lo creamos
                                        firestore.collection("users").document(userId).set(userData)
                                            .addOnSuccessListener {
                                                Log.d(
                                                    "Firestore",
                                                    "Usuario guardado correctamente en Firestore"
                                                )
                                            }.addOnFailureListener {
                                            Log.w(
                                                "Firestore",
                                                "Error al guardar el usuario en Firestore",
                                                it
                                            )
                                        }
                                    } else {
                                        Log.d("Firestore", "El usuario ya existe en Firestore")
                                    }
                                }.addOnFailureListener {
                                Log.w("Firestore", "Error al verificar el usuario en Firestore", it)
                            }
                        }

                    } else {
                        // Maneja el error
                        Log.w("FirebaseAuth", "Inicio de sesión fallido", task.exception)
                    }
                }
        } catch (e: ApiException) {
            Log.w("GoogleSignIn", "Google sign in failed", e)
        }
    }

    fun iniciarSesionConGitHub(context: Context) {
        val auth = FirebaseAuth.getInstance()
        val provider = OAuthProvider.newBuilder("github.com")

        // Inicia el flujo de inicio de sesión
        auth.startActivityForSignInWithProvider(context as Activity, provider.build())
            .addOnSuccessListener { authResult ->
                // Autenticación exitosa
                val user = authResult.user
                // Manejar el usuario autenticado (por ejemplo, almacenar datos del usuario)
            }
            .addOnFailureListener { exception ->
                // Manejar la excepción
                Log.e("Auth", "Error al iniciar sesión con GitHub: ${exception.message}")
                Toast.makeText(context, "Error al iniciar sesión con GitHub", Toast.LENGTH_SHORT).show()
            }
    }

    // Función para iniciar sesión con teléfono
    fun iniciarSesionConTelefono(context: Context) {
        // Aquí puedes usar PhoneAuthProvider de Firebase
        val phoneNumber = "número_de_tel" // Obtén el número de teléfono del usuario
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)       // Número de teléfono a verificar
            .setTimeout(60L, TimeUnit.SECONDS) // Tiempo de espera para la verificación
            .setActivity(context as Activity)   // Actividad que maneja la verificación
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // La verificación fue completada, puedes iniciar sesión
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Manejar el inicio de sesión exitoso
                            } else {
                                // Manejar el error
                            }
                        }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // Manejar la falla en la verificación
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    // El código fue enviado, ahora puedes pedir al usuario que ingrese el código
                    // Puedes guardar el verificationId para usarlo más tarde
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun addTask(task: String) {
        val userId = user.value?.uid ?: return
        val taskData = hashMapOf("task" to task)
        db.collection("tasks").document(userId).collection("userTasks").add(taskData)
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }

}