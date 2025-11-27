package com.example.appnony

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appnony.data.model.PerfilRequest
import com.example.appnony.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class CrearPerfilActivity : ComponentActivity() {

    // 1. Declaramos el lanzador de resultados (ActivityResultLauncher)
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val emailRecibido = intent.getStringExtra("EMAIL_USUARIO") ?: ""

        // 2. Inicializamos el lanzador en onCreate
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent() // Contrato para obtener contenido (imagen)
        ) { uri: Uri? ->
            // Cuando el usuario selecciona una imagen, este bloque se ejecuta
            if (uri != null) {
                // Notificamos a la pantalla de Compose que la URI fue seleccionada
                (application as? App)?.selectedImageUri = uri
            } else {
                Toast.makeText(this, "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show()
            }
        }


        setContent {
            // Pasamos la función 'lanzar galería' como lambda a la pantalla de Compose
            CrearPerfilScreen(
                email = emailRecibido,
                onFinalizar = {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                },
                onImagePickRequest = {
                    // 3. Cuando el botón de la UI se presiona, lanzamos el selector de imágenes
                    imagePickerLauncher.launch("image/*") // Solicita cualquier tipo de imagen
                }
            )
        }
    }
}


@Composable
fun CrearPerfilScreen(
    email: String,
    onFinalizar: () -> Unit,
    onImagePickRequest: () -> Unit // Nueva función para pedir la imagen
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Aquí es donde obtendríamos la URI seleccionada (simulamos con una variable local)
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    var apodo by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // ... (Header and Email fields unchanged) ...

        // --- 📸 BOTÓN PARA SUBIR FOTO ---
        Button(onClick = onImagePickRequest) {
            Text("Seleccionar Foto de Perfil")
        }

        if (selectedImageUri != null) {
            Text("Foto seleccionada. ¡Lista para subir!", color = Color.Green)
            // Aquí iría el AsyncImage (Coil) para mostrar la vista previa
        }

        Spacer(Modifier.height(16.dp))

        // CAMPO APODO (Editable)
        OutlinedTextField(
            value = apodo,
            onValueChange = { apodo = it },
            label = { androidx.compose.material3.Text("Elige un Apodo / Nickname") },
            placeholder = { androidx.compose.material3.Text("Ej. NoniFan") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // BOTÓN GUARDAR
        Button(
            onClick = {
                if (selectedImageUri == null) {
                    Toast.makeText(context, "Selecciona una foto!", Toast.LENGTH_SHORT).show()
                } else {
                    // ... (Lógica de guardado) ...
                }
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            // ... (Cargando / Texto) ...
        }
    }
}
