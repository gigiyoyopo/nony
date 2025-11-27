package com.example.appnony.data.model

import com.squareup.moshi.Json

data class Producto(
    @Json(name = "id_producto") val id: Int,
    @Json(name = "nombre_producto") val nombre: String,
    val precio: Double,
    @Json(name = "imagen_principal") val imagenUrl: String?,
    val talla: String?,
    val stock: Int?
)