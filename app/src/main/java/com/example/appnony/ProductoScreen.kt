package com.example.appnony

import android.content.Intent
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.compareTo

@Composable
fun ProductoScreen(
    navController: NavHostController,
    id: Int,
    nombre: String,
    precio: String,
    imagenRes: Int
) {
    var selectedSize by remember { mutableStateOf<String?>(null) }
    var cantidad by remember { mutableStateOf(1) }
    var mostrarIrCarrito by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val precioDouble = precio.replace("$","").toDoubleOrNull() ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F1F1))
    ) {

        // ---------------------- IMAGEN ----------------------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
        ) {
            // VOLVER
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    painterResource(id = R.drawable.volver),
                    contentDescription = "Volver",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }

            // CORAZÓN + COMPARTIR
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var esFavorito by remember { mutableStateOf(FavoritosState.isFavorite(id)) }

                // CORAZÓN
                Icon(
                    painter = painterResource(
                        id = if (esFavorito) R.drawable.corazonrelleno else R.drawable.corazon
                    ),
                    contentDescription = "Favorito",
                    tint = if (esFavorito) Color.Red else Color.Black,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            FavoritosState.toggle(id)
                            esFavorito = !esFavorito
                        }
                )

                Spacer(Modifier.width(14.dp))

                // COMPARTIR
                IconButton(
                    onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Mira este producto 😍")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.compartir),
                        contentDescription = "Compartir",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        // ---------------------- CONTENIDO ----------------------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp)
        ) {

            Text(nombre, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)

            Spacer(Modifier.height(16.dp))

            // ---------------------- TALLAS ----------------------
            Text("Tallas", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Black)
            Spacer(Modifier.height(8.dp))

            val tallas = listOf("XL", "L", "M", "S", "XS")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(tallas) { talla ->
                    SizeChip(
                        text = talla,
                        selected = selectedSize == talla,
                        onClick = { selectedSize = talla }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---------------------- CONTADOR DE PIEZAS ----------------------
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { if (cantidad > 1) cantidad-- },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) { Text("-", color = Color.Black, fontWeight = FontWeight.Bold) }

                Text("$cantidad", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)

                Button(
                    onClick = { cantidad++ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) { Text("+", color = Color.Black, fontWeight = FontWeight.Bold) }
            }

            Spacer(Modifier.height(20.dp))

            // ---------------------- DETALLES ----------------------
            Text("Detalles del producto", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Spacer(Modifier.height(6.dp))
            Text(
<<<<<<< Updated upstream:app/src/main/java/com/example/appnony/Producto.kt
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed dignissim, metus a facilisis convallis, justo nulla tempor neque, vel ultricies sapien sapien ut nibh.",
                color = Color.Black
=======
                "Detalles del com.example.appnony.producto:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF222222)
            )

            Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                color = Color(0xFF666666)
>>>>>>> Stashed changes:app/src/main/java/com/example/appnony/ProductoScreen.kt
            )

            Spacer(Modifier.height(20.dp))

            // ---------------------- PRECIO + BOTÓN ----------------------
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE6E6E6), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("$${precioDouble * cantidad}", fontWeight = FontWeight.Bold, color = Color.Black)
                }

                Spacer(Modifier.width(8.dp))

                // BOTÓN AÑADIR AL CARRITO
                Button(
                    onClick = {
                        if (selectedSize == null) {
                            Toast.makeText(context, "Selecciona una talla primero", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        CarritoState.addProducto(
                            ProductoCarrito(
                                id = id,
                                nombre = nombre,
                                precio = precio,
                                subtotal = "$${precioDouble * cantidad}",
                                imagenRes = imagenRes,
                                cantidad = cantidad,
                                talla = selectedSize!!
                            )
                        )
                        Toast.makeText(context, "Producto añadido al carrito 🛒", Toast.LENGTH_SHORT).show()
                        mostrarIrCarrito = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Añadir al Carrito", color = Color.White)
                    if (CarritoState.getCantidadTotal() > 0) {
                        Spacer(Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(Color.Red, shape = RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${CarritoState.getCantidadTotal()}", fontSize = 12.sp, color = Color.White)
                        }
                    }
                }
            }

            if (mostrarIrCarrito) {
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { navController.navigate("carrito") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Ir al carrito", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun SizeChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (selected) Color.Black else Color.LightGray,
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected) Color.White else Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}