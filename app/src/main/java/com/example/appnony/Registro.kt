package com.example.appnony

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class Registro : AppCompatActivity() {

    private lateinit var googleClient: GoogleSignInClient
    private val GOOGLE_REQUEST = 100

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

        val btnGoogle = findViewById<LinearLayout>(R.id.btnGoogle)
        val btnFacebook = findViewById<LinearLayout>(R.id.btnFacebook)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleClient = GoogleSignIn.getClient(this, gso)

        btnGoogle.post {
            for (i in 0 until btnGoogle.childCount) {
                val v = btnGoogle.getChildAt(i)
                if (v is TextView) {
                    v.text = "Continuar con Google"
                    v.textSize = 15f
                    v.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    break
                }
            }
        }

        // GOOGLE CLICK
        btnGoogle.setOnClickListener {
            val intent = googleClient.signInIntent
            startActivityForResult(intent, GOOGLE_REQUEST)
        }

        // FACEBOOK CLICK
        btnFacebook.setOnClickListener {
            Toast.makeText(this, "Login con Facebook (aún no configurado).", Toast.LENGTH_SHORT).show()
        }

        // 👉 REGISTRARSE → IR A INICIO
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

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // 👉 TEXTO → IR A LOGIN
        tvIniciarSesion.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_REQUEST) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val email = account?.email ?: "usuario"
                Toast.makeText(this, "Bienvenido: $email", Toast.LENGTH_LONG).show()
            } catch (e: ApiException) {
                Toast.makeText(this, "Error al iniciar con Google", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
