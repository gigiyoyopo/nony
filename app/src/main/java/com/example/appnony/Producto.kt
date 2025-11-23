package com.example.appnony

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image

@Composable
fun ProductoScreen(
    navController: NavHostController,
    id: Int,
    nombre: String,
    precio: Int,
    imagenRes: Int
) {

    var selectedSize by remember { mutableStateOf<String?>(null) }
    var cantidad by remember { mutableStateOf(1) }
    var mostrarIrCarrito by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
    ) {

        // 🖼 IMAGEN + FAVORITO + VOLVER
        Box(
            Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(16.dp)
                .background(Color(0xFFE5E5E5), RoundedCornerShape(16.dp))
        ) {

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    painterResource(id = R.drawable.volver),
                    contentDescription = null,
                    tint = Color(0xFF333333)
                )
            }

            var esFavorito by remember {
                mutableStateOf(FavoritosState.isFavorite(id))
            }

            Icon(
                painterResource(
                    id = if (esFavorito) R.drawable.corazonrelleno else R.drawable.corazon
                ),
                contentDescription = null,
                tint = Color.Unspecified, // ✔ sin filtro, color original
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(32.dp)
                    .clickable {
                        FavoritosState.toggle(id)
                        esFavorito = !esFavorito
                    }
            )

            // 🖼 Imagen SIN desaturación
            Image(
                painter = painterResource(id = imagenRes),
                contentDescription = nombre,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(240.dp)
                    .clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop
            )
        }

        // 📄 CONTENIDO
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFFDFDFD))
                .padding(16.dp)
        ) {

            Text(
                nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF222222)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Tallas",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333)
            )
            Spacer(Modifier.height(10.dp))

            val tallas = listOf("XL", "L", "M", "S", "XS")

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(tallas) { talla ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (selectedSize == talla)
                            Color(0xFF222222)
                        else Color(0xFFE0E0E0),
                        modifier = Modifier.clickable { selectedSize = talla }
                    ) {
                        Text(
                            talla,
                            color = if (selectedSize == talla) Color.White else Color(0xFF222222),
                            modifier = Modifier.padding(12.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // 🔢 CANTIDAD
            Row(verticalAlignment = Alignment.CenterVertically) {

                Button(
                    onClick = { if (cantidad > 1) cantidad-- },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
                ) {
                    Text("-", fontWeight = FontWeight.Bold, color = Color.Black)
                }

                Spacer(Modifier.width(12.dp))

                Text(
                    "$cantidad",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )

                Spacer(Modifier.width(12.dp))

                Button(
                    onClick = { cantidad++ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
                ) {
                    Text("+", fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Detalles del producto:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF222222)
            )

            Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                color = Color(0xFF666666)
            )

            Spacer(Modifier.height(20.dp))

            val context = LocalContext.current

            // 🛒 PRECIO + AÑADIR
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    "$${precio * cantidad}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111111)
                )

                Spacer(Modifier.width(10.dp))

                Button(
                    onClick = {

                        if (selectedSize == null) {
                            Toast.makeText(
                                context,
                                "Selecciona una talla",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }
                        CarritoState.addProducto(
                            ProductoCarrito(
                                id = id,
                                nombre = nombre,
                                precio = "$$precio",
                                subtotal = "$${precio * cantidad}",
                                imagenRes = imagenRes,
                                cantidad = cantidad,
                                talla = selectedSize!!
                            )
                        )

                        mostrarIrCarrito = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF222222))
                ) {
                    Text("Añadir al carrito", color = Color.White)
                }
            }

            if (mostrarIrCarrito) {
                Button(
                    onClick = { navController.navigate("carrito") },
                    modifier = Modifier.padding(top = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF444444))
                ) {
                    Text("Ir al carrito", color = Color.White)
                }
            }
        }
    }
}
