package com.example.nireburuaapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContadorViewModel : ViewModel() {
    // Variable privada para el contador
    private val _contador = MutableLiveData(0)

    // Variable pública que expone el valor del contador como LiveData
    val contador: LiveData<Int> = _contador

    // Función para incrementar el contador
    fun incrementar() {
        _contador.value = (_contador.value ?: 0) + 1
    }

    // Función para decrementar el contador
    fun decrementar() {
        _contador.value = (_contador.value ?: 0) - 1
    }

    // Función para resetear el contador
    fun resetear() {
        _contador.value = 0
    }
}