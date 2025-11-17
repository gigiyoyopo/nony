package com.example.appnony

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController

@Composable
fun CarritoScreen(navController: NavHostController) {
    val productos by remember { derivedStateOf { CarritoState.items.values.toList() } }
    var subtotal by remember { mutableStateOf(CarritoState.getTotal()) }
    var searchQuery by remember { mutableStateOf("") } // <-- ahora coincide con el OutlinedTextField

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color(0xFFF1F1F1))
            .padding(16.dp)
    ) {
        // ----------------- Buscador -----------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                textStyle = TextStyle(color = Color.Black),
                placeholder = {
                    Text(
                        "Buscar...",
                        color = Color.Black.copy(alpha = 0.45f),
                        textAlign = TextAlign.Start
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.lupa),
                        contentDescription = null,
                        tint = Color.Black
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFFF3F3F3), RoundedCornerShape(8.dp))
                    .clickable { /* TODO: filtros */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(id = R.drawable.filtros),
                    contentDescription = "Filtros",
                    tint = Color.Black
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // ----------------- Título -----------------
        Text("Carrito de Compra", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.Black)
        Spacer(Modifier.height(16.dp))

        // ----------------- Lista de Productos -----------------
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.weight(1f)) {
            items(productos) { prod ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = prod.imagenRes),
                            contentDescription = prod.nombre,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.width(12.dp))

                        // ----------------- Detalles del producto -----------------
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(prod.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                                Spacer(Modifier.width(8.dp))
                                Text("Talla: ${prod.talla}", fontSize = 14.sp, color = Color.Black)
                            }
                            Spacer(Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(prod.precio, fontSize = 14.sp, color = Color.Black)
                                Spacer(Modifier.width(16.dp))
                                Text(prod.subtotal, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }

                        // ----------------- Contador y eliminar -----------------
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray)
                                        .clickable { CarritoState.updateCantidad("${prod.id}_${prod.talla}", prod.cantidad - 1) },
                                    contentAlignment = Alignment.Center
                                ) { Text("-", fontWeight = FontWeight.Bold) }

                                Text("${prod.cantidad}", fontWeight = FontWeight.Bold, fontSize = 16.sp)

                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray)
                                        .clickable { CarritoState.updateCantidad("${prod.id}_${prod.talla}", prod.cantidad + 1) },
                                    contentAlignment = Alignment.Center
                                ) { Text("+", fontWeight = FontWeight.Bold) }
                            }

                            Spacer(Modifier.height(4.dp))

                            Button(
                                onClick = { CarritoState.removeProducto("${prod.id}_${prod.talla}") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) { Text("Eliminar", color = Color.White, fontSize = 12.sp) }
                        }
                    }
                }
            }
        }

        // ----------------- Totales -----------------
        val envio = 45.0
        subtotal = CarritoState.getTotal()
        val iva = subtotal * 0.16
        val total = subtotal + envio + iva

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Subtotal", fontSize = 14.sp, color = Color.Black)
                Text("$${"%.2f".format(subtotal)}", fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Costo de envío", fontSize = 14.sp, color = Color.Black)
                Text("$${"%.2f".format(envio)}", fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("IVA", fontSize = 14.sp, color = Color.Black)
                Text("$${"%.2f".format(iva)}", fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text("$${"%.2f".format(total)}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: pago */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("PAGAR AHORA", color = Color.White)
        }
    }
}