package com.example.appnony

import androidx.compose.runtime.mutableStateMapOf

data class ProductoCarrito(
    val id: Int,
    val nombre: String,
    val precio: String,
    var subtotal: String,
    val imagenRes: Int,
    var cantidad: Int,
    val talla: String
) {
    val precioDouble: Double
        get() = precio.replace("$", "").toDoubleOrNull() ?: 0.0
}

object CarritoState {
    // Clave: id+talla para que productos con distintas tallas sean separados
    val items = mutableStateMapOf<String, ProductoCarrito>()

    fun addProducto(producto: ProductoCarrito) {
        val key = "${producto.id}_${producto.talla}"
        if (items.containsKey(key)) {
            val existing = items[key]!!
            val newCantidad = existing.cantidad + producto.cantidad
            items[key] = existing.copy(
                cantidad = newCantidad,
                subtotal = "$${existing.precioDouble * newCantidad}"
            )
        } else {
            items[key] = producto
        }
    }

    fun removeProducto(key: String) {
        items.remove(key)
    }

    fun updateCantidad(key: String, cantidad: Int) {
        val prod = items[key]
        if (prod != null) {
            if (cantidad <= 0) removeProducto(key)
            else items[key] = prod.copy(
                cantidad = cantidad,
                subtotal = "$${prod.precioDouble * cantidad}"
            )
        }
    }

    fun getTotal(): Double = items.values.sumOf { it.precioDouble * it.cantidad }
    fun getCantidadTotal(): Int = items.values.sumOf { it.cantidad }
}