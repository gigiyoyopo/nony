package com.example.appnony

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class Registro : AppCompatActivity() {

    // ➡️ VARIABLES DE GOOGLE SIGN-IN
    private val TAG = "GoogleSignInRegistro"
    private val ID_CLIENTE_WEB = "412065217354-l1ue9621olcghpo81kjpsiven1l6tpr9.apps.googleusercontent.com"
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    // ⬅ FIN DE VARIABLES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Inicializaciones de la interfaz... (mantener aquí)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarse)
        val tvIniciarSesion = findViewById<TextView>(R.id.tvIniciarSesion)
        val signInButton: SignInButton = findViewById(R.id.sign_in_google_button_registro)

        // Inicializaciones de Google Sign-In (como las teníamos)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(ID_CLIENTE_WEB)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

        signInButton.setOnClickListener {
            signIn()
        }

        // ... Resto de tu lógica onCreate ...
        tvIniciarSesion.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)


            val nombreUsuario = account.displayName ?: "Usuario Google"
            val correoUsuario = account.email ?: ""
            val fotoPerfilUrl = account.photoUrl?.toString() ?: ""

            // GUARDAMOS EN SHARED PREFERENCES
            guardarDatosUsuario(nombreUsuario, correoUsuario, fotoPerfilUrl)

            // Navegación a la pantalla principal
            Log.d(TAG, "Registro con Google exitoso. Nombre: $nombreUsuario")
            Toast.makeText(this, "Registro con Google exitoso. Bienvenido(a), $nombreUsuario", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        } catch (e: ApiException) {
            Log.w(TAG, "Registro Google fallido. Código: " + e.statusCode)
            Toast.makeText(this, "No se pudo registrar con Google. Error: ${e.statusCode}", Toast.LENGTH_SHORT).show()
        }
    }

    // ------------------------------------------------------------------
    // PARA KATTO SI
    // ------------------------------------------------------------------
    private fun guardarDatosUsuario(nombre: String, correo: String, fotoUrl: String) {
        val prefs = getSharedPreferences("AUTH_PREFS", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString("nombre_usuario", nombre)
            putString("correo_usuario", correo)
            putString("foto_url", fotoUrl)
            apply()
        }
    }
}