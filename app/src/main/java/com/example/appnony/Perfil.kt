package com.example.appnony

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.navigation.NavHostController
import com.example.appnony.Registro

@Composable
fun PerfilScreen(
    navController: NavHostController,
    nombreUsuario: String = "Nombre de Usuario"
) {
    val context = LocalContext.current
    var mostrarDialogo by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
            .padding(16.dp)
    ) {

        /* ----------------- TÍTULO ----------------- */

        Text(
            text = "Perfil",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        /* ----------------- AVATAR ----------------- */

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_placeholder),
                    contentDescription = "Avatar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = nombreUsuario,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        /* ----------------- CERRAR SESIÓN ----------------- */

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Red)
                .clickable { mostrarDialogo = true }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Cerrar sesión",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        /* ----------------- DIALOGO CONFIRMACION ----------------- */
        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = { Text("Cerrar sesión") },
                text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mostrarDialogo = false
                            // Redirige a RegistroActivity
                            val intent = Intent(context, Registro::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(intent)
                        }
                    ) {
                        Text("Sí", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("No", color = Color.Black)
                    }
                }
            )
        }
    }
}
