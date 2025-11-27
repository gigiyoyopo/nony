package com.example.appnony.data.network

import com.example.appnony.data.model.Producto
import com.example.appnony.data.model.PerfilRequest
import com.example.appnony.data.model.LoginRequest
import com.example.appnony.data.model.RegistroRequest
import com.example.appnony.data.model.RespuestaApi
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // 1. OBTENER PRODUCTOS
    @GET("https://obtenerproductos-6eeugq25eq-uc.a.run.app")
    suspend fun obtenerProductos(): List<Producto>

    // 2. CREAR PERFIL
    // CORREGIDO: El nombre de la función debe ser 'crearPerfilNoni' (minúscula)
    @POST("https://crearperfilnoni-6eeugq25eq-uc.a.run.app")
    suspend fun crearPerfilnoni(@Body request: PerfilRequest): RespuestaApi

    // 3. REGISTRAR USUARIO
    @POST("https://registrarusuario-6eeugq25eq-uc.a.run.app")
    suspend fun registrarUsuario(@Body request: RegistroRequest): RespuestaApi

    @POST("https://loginusuario-6eeugq25eq-uc.a.run.app/")
    suspend fun loginUsuario(@Body request: LoginRequest): RespuestaApi
}