package com.example.appnony

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Para usar Corrutinas (segundo plano)
import com.example.appnony.data.model.RegistroRequest
import com.example.appnony.data.network.RetrofitInstance
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

class Registro : AppCompatActivity() {

    private lateinit var googleClient: GoogleSignInClient
    private val GOOGLE_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // 1. OBTENER LAS REFERENCIAS DE LOS CAMPOS
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellidos = findViewById<EditText>(R.id.etApellidos)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val etCP = findViewById<EditText>(R.id.etCodigoPostal)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarse)
        val tvIniciarSesion = findViewById<TextView>(R.id.tvIniciarSesion)


        // Configuración de Google (opcional por ahora)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        googleClient = GoogleSignIn.getClient(this, gso)

        // 2. LA ACCIÓN QUE ENVÍA LOS DATOS (Aquí ocurre la magia)
        btnRegistrar.setOnClickListener {
            // A. Leemos lo que escribió el usuario
            val nombre = etNombre.text.toString().trim()
            val apellidos = etApellidos.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()
            val cp = etCP.text.toString().trim()

            // B. Validamos que no esté vacío
            if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor llena los campos obligatorios (*)", Toast.LENGTH_SHORT).show()
            } else {
                // C. Lanzamos la petición a internet (Corrutina)
                lifecycleScope.launch {
                    try {
                        // --- PASO 1: EMPAQUETAR DATOS ---
                        val datosParaEnviar = RegistroRequest(
                            nombre = nombre,
                            apellidos = apellidos,
                            telefono = telefono,
                            correo = correo,
                            contrasena = contrasena,
                            cp = cp
                        )

                        // --- PASO 2: ENVIAR A LA NUBE ---
                        // Llamamos a la función que creamos en ApiService
                        val respuesta = RetrofitInstance.api.registrarUsuario(datosParaEnviar)

                        // --- PASO 3: SI LLEGA AQUÍ, FUE UN ÉXITO ---
                        Toast.makeText(applicationContext, "¡Registro Exitoso! Ahora inicia sesión.", Toast.LENGTH_LONG).show()

                        // Nos vamos al Login para que entre
                        val intent = Intent(this@Registro, Login::class.java)
                        startActivity(intent)
                        finish() // Cerramos registro

                    } catch (e: Exception) {
                        // --- PASO 4: MANEJO DE ERRORES ---
                        // Si falla internet, o el correo ya existe, cae aquí
                        Toast.makeText(applicationContext, "Error: No se pudo registrar. ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }



        // Ir a Login
        tvIniciarSesion.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}