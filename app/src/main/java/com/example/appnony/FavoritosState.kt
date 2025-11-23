package com.example.appnony

import androidx.compose.runtime.mutableStateListOf

object FavoritosState {

    private val favoritos = mutableStateListOf<Int>()

    fun toggle(id: Int) {
        if (favoritos.contains(id)) favoritos.remove(id)
        else favoritos.add(id)
    }

    fun isFavorite(id: Int) = favoritos.contains(id)

    fun getFavoritosList(productos: List<ProductoSimple>): List<ProductoSimple> {
        return productos.filter { favoritos.contains(it.id) }
    }
}
