package com.example.appnony

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

// Google Sign-In
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class registro : AppCompatActivity() {

    private lateinit var googleClient: GoogleSignInClient
    private val GOOGLE_REQUEST = 100  // Código de resultado para Google

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Enlaces del XML
        val nombre = findViewById<EditText>(R.id.etNombre)
        val apellidos = findViewById<EditText>(R.id.etApellidos)
        val telefono = findViewById<EditText>(R.id.etTelefono)
        val correo = findViewById<EditText>(R.id.etCorreo)
        val contrasena = findViewById<EditText>(R.id.etContrasena)
        val codigoPostal = findViewById<EditText>(R.id.etCodigoPostal)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarse)
        val btnGoogle = findViewById<com.google.android.gms.common.SignInButton>(R.id.btnGoogle)
        val btnFacebook = findViewById<Button>(R.id.btnFacebook)
        val tvIniciarSesion = findViewById<TextView>(R.id.tvIniciarSesion)

        // -------------------------------
        // ⭐ CONFIGURACIÓN GOOGLE SIGN-IN
        // -------------------------------
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleClient = GoogleSignIn.getClient(this, googleConf)


        btnGoogle.setOnClickListener {
            val intent = googleClient.signInIntent
            startActivityForResult(intent, GOOGLE_REQUEST)
            val googleText = btnGoogle.getChildAt(0) as TextView
            googleText.text = "Continuar con Google"
            googleText.textSize = 16f
            googleText.setPadding(0, 0, 0, 0)
            googleText.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        }


        btnFacebook.setOnClickListener {
            Toast.makeText(this, "Facebook aún no está configurado.", Toast.LENGTH_SHORT).show()
        }


        btnRegistrar.setOnClickListener {
            if (
                nombre.text.isEmpty() ||
                apellidos.text.isEmpty() ||
                telefono.text.isEmpty() ||
                correo.text.isEmpty() ||
                contrasena.text.isEmpty() ||
                codigoPostal.text.isEmpty()
            ) {
                Toast.makeText(this, "Por favor completa todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Registro exitoso (simulado).", Toast.LENGTH_SHORT).show()
            }
        }


        tvIniciarSesion.setOnClickListener {
            Toast.makeText(this, "Aquí iría la pantalla de inicio de sesión.", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_REQUEST) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                val email = account?.email

                Toast.makeText(this, "Bienvenido: $email", Toast.LENGTH_LONG).show()

            } catch (e: ApiException) {
                Toast.makeText(this, "Error al iniciar con Google", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
