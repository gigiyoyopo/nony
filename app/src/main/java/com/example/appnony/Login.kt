package com.example.appnony

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Para usar Corrutinas (API)
import com.example.appnony.data.model.LoginRequest
import com.example.appnony.data.model.UsuarioData
import com.example.appnony.data.network.RetrofitInstance
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    // ➡ Variables de Google (DEBES TENER ESTO EN TU setup)
    private val TAG = "GoogleSignInRopa"
    private val ID_CLIENTE_WEB = "412065217354-l1ue9621olcghpo81kjpsiven1l6tpr9.apps.googleusercontent.com"
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    private val GOOGLE_REQUEST = 200
    // ⬅ FIN DE VARIABLES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // --- REFERENCIAS XML (Conectando tus IDs) ---
        val inputCorreo = findViewById<TextInputEditText>(R.id.InputCorreo)
        val inputContrasena = findViewById<TextInputEditText>(R.id.InputContrasena)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val tvRegistro = findViewById<TextView>(R.id.tvRegistro)
        val btnGoogle = findViewById<com.google.android.gms.common.SignInButton>(R.id.sign_in_google_button)

        // Configuración de Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(ID_CLIENTE_WEB).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
        btnGoogle.setOnClickListener { signIn() }


        // --- 1. LÓGICA DE LOGIN MANUAL (CONECTADO A API) ---
        btnIngresar.setOnClickListener {
            val correo = inputCorreo.text.toString().trim()
            val contrasena = inputContrasena.text.toString().trim()

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor escribe correo y contraseña", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    try {
                        val request = LoginRequest(correo, contrasena)

                        // Llama a la función loginUsuario para validar
                        val response = RetrofitInstance.api.loginUsuario(request)

                        // Manejo de la nulidad: Verificamos si la respuesta trae datos de usuario
                        val usuarioVerificado = response.usuario

                        if (usuarioVerificado != null) {
                            // ÉXITO: Guardamos la sesión con el nombre verificado
                            guardarDatosUsuario(
                                nombre = usuarioVerificado.nombre,
                                correo = usuarioVerificado.email,
                                fotoUrl = "" // No hay foto de perfil manual al ingresar
                            )

                            Toast.makeText(applicationContext, "¡Login Correcto!", Toast.LENGTH_SHORT).show()
                            irAlMain()

                        } else {
                            // La API respondió OK, pero sin datos (Error de servidor interno, pero no credenciales)
                            Toast.makeText(applicationContext, "Error en la respuesta del servidor.", Toast.LENGTH_LONG).show()
                        }

                    } catch (e: Exception) {
                        // Falla (401 Unauthorized - Credenciales incorrectas, o fallo de red)
                        Toast.makeText(applicationContext, "Error: Credenciales incorrectas o fallo de red.", Toast.LENGTH_LONG).show()
                        Log.e(TAG, "Login API Error: ${e.message}")
                    }
                }
            }
        }

        // --- 2. NAVEGACIÓN ---
        tvRegistro.setOnClickListener {
            startActivity(Intent(this, Registro::class.java))
        }
    }

    // 🔑 CHEQUEO DE SESIÓN AL INICIAR LA ACTIVITY
    override fun onStart() {
        super.onStart()

        // 1. VERIFICAR SESIÓN MANUAL
        if (isLocalSessionActive()) {
            irAlMain()
            return
        }

        // 2. VERIFICAR SESIÓN DE GOOGLE
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            updateUI(account)
        }
    }

    // --- FUNCIONES AUXILIARES ---

    private fun isLocalSessionActive(): Boolean {
        val prefs = getSharedPreferences("AUTH_PREFS", Context.MODE_PRIVATE)
        return prefs.contains("correo_usuario")
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val correoUsuario = account.email ?: ""
            // Si Google funciona, asumimos que es correcto y guardamos la sesión
            guardarDatosUsuario(account.displayName ?: "Usuario Google", correoUsuario, account.photoUrl?.toString() ?: "")
            updateUI(account)
        } catch (e: ApiException) {
            Toast.makeText(this, "Fallo Google. Error: ${e.statusCode}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            irAlMain()
        }
    }

    private fun irAlMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

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