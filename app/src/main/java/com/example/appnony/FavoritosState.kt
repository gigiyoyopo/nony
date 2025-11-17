package com.example.appnony

import androidx.compose.runtime.mutableStateListOf

// Esta lista vive en memoria y la usan Inicio, Producto y Favoritos
object FavoritosState {

    // Guarda solo los IDs marcados como favorito
    val favoritos = mutableStateListOf<Int>()

    fun toggle(id: Int) {
        if (favoritos.contains(id)) {
            favoritos.remove(id)
        } else {
            favoritos.add(id)
        }
    }

    fun isFavorite(id: Int): Boolean {
        return favoritos.contains(id)
    }

    fun getFavoritosList(productos: List<ProductoSimple>): List<ProductoSimple> {
        return productos.filter { favoritos.contains(it.id) }
    }
}