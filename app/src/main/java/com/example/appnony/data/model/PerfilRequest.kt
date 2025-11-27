package com.example.appnony.data.model

data class PerfilRequest(
    val email: String,
    val apodo: String,
    val fotoUrl: String? = null
)