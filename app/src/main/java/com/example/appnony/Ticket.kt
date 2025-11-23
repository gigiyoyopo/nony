package com.example.appnony

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun TicketScreen(navController: NavHostController) {
    val productos by remember { derivedStateOf { CarritoState.items.values.toList() } }
    val envio = 45.0
    val subtotal = CarritoState.getTotal()
    val iva = subtotal * 0.16
    val total = subtotal + envio + iva

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp)) // baja el título por notificaciones

        // 🏷 Logo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo App",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "TICKET DE COMPRA",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF222222),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ------------------ Ticket digital ------------------
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                productos.forEach { prod ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(prod.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                            Text("Talla: ${prod.talla}", fontSize = 14.sp, color = Color.Black)
                            Text("Precio unitario: ${prod.precio}", fontSize = 14.sp, color = Color.Black)
                            Text("Cantidad: ${prod.cantidad}", fontSize = 14.sp, color = Color.Black)
                        }
                        Text(prod.subtotal, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Totales dentro del ticket
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
                    Text("TOTAL", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF111111))
                    Text("$${"%.2f".format(total)}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF111111))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("fin") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF222222))
        ) {
            Text("FINALIZAR", color = Color.White)
        }
    }
}
