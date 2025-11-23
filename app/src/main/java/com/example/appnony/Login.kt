package com.example.appnony

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputCorreo = findViewById<TextInputEditText>(R.id.InputCorreo)
        val inputContrasena = findViewById<TextInputEditText>(R.id.InputContrasena)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)

        // 🔹 BOTÓN INGRESAR -> MainActivity
        btnIngresar.setOnClickListener {

            val correo = inputCorreo.text.toString().trim()
            val contrasena = inputContrasena.text.toString().trim()

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // 🔹 TEXTO REGISTRATE -> Registro.kt
        tvRegistro.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }
}

