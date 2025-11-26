package com.example.appnony

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun FavoritosScreen(
    navController: NavHostController,
    productosList: List<ProductoSimple>
) {

    val favoritosLive by remember {
        derivedStateOf {
            FavoritosState.getFavoritosList(productosList)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
            .padding(16.dp)
    ) {

        /* ----------------- REGRESAR + TÍTULO (IGUAL QUE PERFIL) ----------------- */

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
                text = "Favoritos",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))


        /* ----------------- LISTA DE FAVORITOS ----------------- */

        if (favoritosLive.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Aún no tienes favoritos",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

        } else {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                items(favoritosLive) { prod ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clickable {
                                navController.navigate(
                                    "com.example.appnony.producto/${prod.id}/${prod.nombre}/${prod.precio}/${prod.imagenRes}"
                                )
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEDEDED)  // MÁS GRIS
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                painter = painterResource(id = prod.imagenRes),
                                contentDescription = prod.nombre,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    prod.nombre,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    prod.precio,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }

                            /* ❤️ ÍCONO DE CORAZÓN MÁS GRANDE */

                            Icon(
                                painter = painterResource(
                                    id = if (FavoritosState.isFavorite(prod.id))
                                        R.drawable.corazonrelleno
                                    else
                                        R.drawable.corazon
                                ),
                                contentDescription = "Favorito",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(36.dp)      // <-- MÁS GRANDE
                                    .padding(12.dp)
                                    .clickable {
                                        FavoritosState.toggle(prod.id)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}