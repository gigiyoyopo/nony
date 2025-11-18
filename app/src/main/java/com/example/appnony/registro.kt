package com.example.appnony

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import android.widget.Toast

class registro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val nombre = findViewById<EditText>(R.id.etNombre)
        val apellidos = findViewById<EditText>(R.id.etApellidos)
        val telefono = findViewById<EditText>(R.id.etTelefono)
        val correo = findViewById<EditText>(R.id.etCorreo)
        val contrasena = findViewById<EditText>(R.id.etContrasena)
        val codigoPostal = findViewById<EditText>(R.id.etCodigoPostal)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarse)
        val tvIniciarSesion = findViewById<TextView>(R.id.tvIniciarSesion)

        btnRegistrar.setOnClickListener {
            if (
                nombre.text.isEmpty() ||
                apellidos.text.isEmpty() ||
                telefono.text.isEmpty() ||
                correo.text.isEmpty() ||
                contrasena.text.isEmpty() ||
                codigoPostal.text.isEmpty()
            ) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Registro exitoso (simulado).", Toast.LENGTH_SHORT).show()
            }
        }

        tvIniciarSesion.setOnClickListener {
            Toast.makeText(this, "Aquí iría la pantalla de inicio de sesión.", Toast.LENGTH_SHORT).show()
        }
    }
}