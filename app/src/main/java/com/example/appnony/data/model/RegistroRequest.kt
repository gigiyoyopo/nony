package com.example.appnony.data.model

data class RegistroRequest(
    val nombre: String,
    val apellidos: String,
    val telefono: String,
    val correo: String,
    val contrasena: String,
    val cp: String
)