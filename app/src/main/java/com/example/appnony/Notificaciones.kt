package com.example.appnony

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.example.appnony.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class Notificacion(
    val id: Int,
    val titulo: String,
    val mensaje: String,
    val imagen: Int? = null
)

@Composable
fun NotificacionesScreen(navController: NavHostController) {

    val notificaciones = remember {
        listOf(
            Notificacion(
                id = 1,
                titulo = "¡Nuevo descuento disponible!",
                mensaje = "Tenemos un 30% en toda la colección de invierno.",
                imagen = R.drawable.promo1
            ),
            Notificacion(
                id = 2,
                titulo = "Tu pedido fue enviado",
                mensaje = "Tu orden #2024 está en camino."
            ),
            Notificacion(
                id = 3,
                titulo = "Sugerencias para ti",
                mensaje = "Basado en tus favoritos, te recomendamos nuevos productos.",
                imagen = R.drawable.promo2
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
            .padding(16.dp)
    ) {

        /* ----------------- REGRESAR + TÍTULO ----------------- */
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.volver),
                    contentDescription = "Regresar",
                    tint = Color.Black
                )
            }
            Text(
                text = "Notificaciones",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        /* ----------------- LISTA DE NOTIFICACIONES ----------------- */
        if (notificaciones.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No hay notificaciones",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(notificaciones) { noti ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clickable { /* TODO: detalle de notificación */ },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (noti.imagen != null) {
                                Image(
                                    painter = painterResource(id = noti.imagen),
                                    contentDescription = noti.titulo,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFFE2E2E2)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "No Img",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    noti.titulo,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    noti.mensaje,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }

                            /* ⚡️ ÍCONO opcional (ej: alerta o info) */
                            Icon(
                                painter = painterResource(id = R.drawable.info),
                                contentDescription = "Info",
                                tint = Color.Blue,
                                modifier = Modifier
                                    .size(36.dp)
                                    .padding(12.dp)
                                    .clickable { /* TODO: acción */ }
                            )
                        }
                    }
                }
            }
        }
    }
}