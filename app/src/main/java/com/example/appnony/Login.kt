package com.example.appnony

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
import com.google.android.material.textfield.TextInputEditText

class Login : AppCompatActivity() {

    // ➡NO ME TOQUEN ACA PORFA
    private val TAG = "GoogleSignInRopa"
    private val ID_CLIENTE_WEB = "412065217354-l1ue9621olcghpo81kjpsiven1l6tpr9.apps.googleusercontent.com"
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    // ⬅ FIN DE VARIABLES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

        val signInButton: SignInButton = findViewById(R.id.sign_in_google_button)
        signInButton.setOnClickListener {
            signIn()
        }

        // Resto de inicializaciones de la interfaz...
        val inputCorreo = findViewById<TextInputEditText>(R.id.InputCorreo)
        val inputContrasena = findViewById<TextInputEditText>(R.id.InputContrasena)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)

        btnIngresar.setOnClickListener {
            // ... (tu lógica de login manual) ...
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvRegistro.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
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
            // Si la URL de la foto es nula, guardamos una cadena vacía
            val fotoPerfilUrl = account.photoUrl?.toString() ?: ""

            // GUARDAMOS EN SHARED PREFERENCES
            guardarDatosUsuario(nombreUsuario, correoUsuario, fotoPerfilUrl)

            // Continuamos con la navegación
            updateUI(account)

        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "No se pudo iniciar sesión con Google. Error: ${e.statusCode}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            Toast.makeText(this, "Bienvenido(a), ${account.displayName}", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // ------------------------------------------------------------------
    // PARA LA CACHONDA DE KATTO ACA ESTAN TUS VAR
    // ------------------------------------------------------------------
    private fun guardarDatosUsuario(nombre: String, correo: String, fotoUrl: String) {
        val prefs = getSharedPreferences("AUTH_PREFS", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString("nombre_usuario", nombre)
            putString("correo_usuario", correo)
            putString("foto_url", fotoUrl)
            apply() // Aplica los cambios inmediatamente
        }
    }
}