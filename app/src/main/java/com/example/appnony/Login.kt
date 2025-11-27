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
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText

class Login : AppCompatActivity() {

    private val TAG = "GoogleSignInRopa"
    private val ID_CLIENTE_WEB = "412065217354-l1ue9621olcghpo81kjpsiven1l6tpr9.apps.googleusercontent.com"
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>
    // ⬅ FIN DE VARIABLES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }


        btnIngresar.setOnClickListener {
                        }

        tvRegistro.setOnClickListener {
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
            val correoUsuario = account.email ?: ""
            updateUI(account)
        } catch (e: ApiException) {
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
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
        }
    }
}