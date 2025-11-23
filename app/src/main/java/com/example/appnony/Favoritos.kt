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

@Composable
fun FavoritosScreen(
    navController: NavHostController,
    productosList: List<ProductoSimple>
) {

    val favoritos by remember {
        derivedStateOf { FavoritosState.getFavoritosList(productosList) }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7)) // fondo escala de grises de gigi
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painterResource(id = R.drawable.volver),
                    contentDescription = null,
                    tint = Color(0xFF333333)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                "Favoritos",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            )
        }

        Spacer(Modifier.height(20.dp))

        if (favoritos.isEmpty()) {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                Text(
                    "Aún no tienes favoritos",
                    color = Color(0xFF555555)
                )
            }
        } else {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(favoritos) { prod ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clickable {
                                navController.navigate(
                                    "producto/${prod.id}/${prod.nombre}/${prod.precio}/${prod.imagenRes}"
                                )
                            },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF0F0F0)
                        ),
                        elevation = CardDefaults.cardElevation(3.dp)
                    ) {

                        Row(
                            Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Imagen SIN desaturación
                            Image(
                                painter = painterResource(id = prod.imagenRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Column(Modifier.weight(1f)) {
                                Text(
                                    prod.nombre,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF222222)
                                )
                                Text(
                                    "$${prod.precio}",
                                    fontSize = 14.sp,
                                    color = Color(0xFF555555)
                                )
                            }

                            Icon(
                                painterResource(
                                    id = if (FavoritosState.isFavorite(prod.id))
                                        R.drawable.corazonrelleno
                                    else R.drawable.corazon
                                ),
                                contentDescription = null,
                                tint = Color.Unspecified, // ✔ color original
                                modifier = Modifier
                                    .padding(12.dp)
                                    .size(32.dp)
                                    .clickable { FavoritosState.toggle(prod.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
