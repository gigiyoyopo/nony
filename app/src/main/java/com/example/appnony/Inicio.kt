package com.example.appnony

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.div

// -----------------------------
// MODELO DE PRODUCTO
// -----------------------------
data class ProductoSimple(
    val id: Int,
    val nombre: String,
    val precio: String,
    val imagenRes: Int
)

@Composable
fun InicioScreen(
    navController: NavHostController,
    productosList: List<ProductoSimple>,   // <-- AHORA SE RECIBE DESDE MainActivity
    nombreUsuario: String = "Nombre de Usuario"
) {

    var searchQuery by remember { mutableStateOf("") }

    val categorias = listOf("Popular", "Mujer", "Hombre", "Bebé", "Niños y Niñas")
    var seleccionCategoria by remember { mutableStateOf("Popular") }

    val promoReal = listOf(
        R.drawable.promo1,
        R.drawable.promo2,
        R.drawable.promo3
    )

    val promoLoop = remember { List(1000) { promoReal[it % promoReal.size] } }
    val startIndex = promoLoop.size / 2
    val carouselState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)

    // 👇 AHORA productos vienen de MainActivity
    val productos = productosList

    // Auto Scroll
    LaunchedEffect(Unit) {
        while (isActive) {
            delay(3000)
            val next = carouselState.firstVisibleItemIndex + 1
            carouselState.animateScrollToItem(next)
            if (next >= promoLoop.size - promoReal.size) carouselState.scrollToItem(startIndex)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        // ===========================
        // HEADER
        // ===========================
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { navController.navigate("perfil") },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
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
                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text("Bienvenido,", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Text(
                        nombreUsuario,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = { navController.navigate("carrito") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.carrito),
                        contentDescription = "Carrito",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { navController.navigate("notificaciones") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.notificacion),
                        contentDescription = "Notificaciones",
                        tint = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ===========================
        // BUSCADOR
        // ===========================
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
                    .clickable {},
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(id = R.drawable.filtros),
                    contentDescription = "Filtros",
                    tint = Color.Black
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // ===========================
        // TÍTULO "CATEGORÍAS"
        // ===========================
        Text(
            "Categorías",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        // ===========================
        // CATEGORÍAS
        // ===========================
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(categorias) { _, cat ->
                Button(
                    onClick = { seleccionCategoria = cat },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (seleccionCategoria == cat) Color.Black else Color(0xFFEDEDED)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        cat,
                        color = if (seleccionCategoria == cat) Color.White else Color.Black
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // ===========================
        // CARRUSEL
        // ===========================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            LazyRow(
                state = carouselState,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                itemsIndexed(promoLoop) { _, imgRes ->
                    Image(
                        painter = painterResource(imgRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(320.dp)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            "Descubre lo que tenemos para ti",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))

        // ===========================
        // GRID DE PRODUCTOS
        // ===========================
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {

            items(productos) { prod ->

                val esFavorito by remember { derivedStateOf { FavoritosState.isFavorite(prod.id) } }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clickable {
                            navController.navigate(
                                "producto/${prod.id}/${prod.nombre}/${prod.precio}/${prod.imagenRes}"
                            )
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFBFBFA)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {

                    Box(Modifier.fillMaxSize()) {

                        Column(modifier = Modifier.padding(8.dp)) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF0F0F0))
                            ) {
                                Image(
                                    painter = painterResource(id = prod.imagenRes),
                                    contentDescription = prod.nombre,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(Modifier.height(8.dp))

                            Text(
                                prod.nombre,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Text(
                                prod.precio,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        // ❤️ CORAZÓN EN LA ESQUINA DERECHA
                        Icon(
                            painter = painterResource(
                                if (esFavorito) R.drawable.corazonrelleno else R.drawable.corazon
                            ),
                            contentDescription = "Favorito",
                            tint = if (esFavorito) Color.Red else Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.TopEnd)
                                .padding(6.dp)
                                .clickable {
                                    FavoritosState.toggle(prod.id)
                                }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        // ===========================
        // BARRA INFERIOR
        // ===========================
        Row(
            Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(44.dp)
                    .background(Color.Black, RoundedCornerShape(12.dp))
                    .clickable {},
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(id = R.drawable.inicio),
                    contentDescription = "Inicio",
                    tint = Color.White
                )
            }

            BottomIcon(R.drawable.corazon) { navController.navigate("favoritos") }

            BottomIcon(R.drawable.carrito) { navController.navigate("carrito") }

            BottomIcon(R.drawable.perfil) { navController.navigate("perfil") }
        }
    }
}

@Composable
fun BottomIcon(iconRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(Color(0xFFF3F3F3), RoundedCornerShape(8.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(22.dp)
        )
    }
}