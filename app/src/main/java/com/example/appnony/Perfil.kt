package com.example.appnony

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.navigation.NavHostController

@Composable
fun PerfilScreen(
    navController: NavHostController,
    nombreUsuario: String = "Nombre de Usuario"
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
            .padding(16.dp)
    ) {

        /* ----------------- REGRESAR + TÍTULO ----------------- */

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.volver),
                    contentDescription = "Regresar",
                    tint = Color.Black
                )
            }

            Text(
                text = "Perfil",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

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

        /* ----------------- OPCIONES DEL PERFIL ----------------- */

        PerfilOpcion("Editar perfil") { /* TODO */ }
        PerfilOpcion("Direcciones") { /* TODO */ }
        PerfilOpcion("Pedidos") { /* TODO */ }
        PerfilOpcion("Métodos de pago") { /* TODO */ }
        PerfilOpcion("Cerrar sesión") { /* TODO */ }
    }
}

@Composable
fun PerfilOpcion(texto: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF5F5F5))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(texto, fontSize = 16.sp, color = Color.Black)
    }

    Spacer(modifier = Modifier.height(12.dp))
}