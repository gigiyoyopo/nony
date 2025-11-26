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

<<<<<<< Updated upstream
// -----------------------------
// MODELO DE PRODUCTO
// -----------------------------
=======
// --- CAMBIO CLAVE: USAMOS MATERIAL 2 (La versión clásica) ---
import androidx.compose.material.* // Esto importa Button, Card, Text, Icon, etc. de la versión 2
// -----------------------------------------------------------

// Modelo temporal
>>>>>>> Stashed changes
data class ProductoSimple(
    val id: Int,
    val nombre: String,
    val precio: String,
    val imagenRes: Int
)

@Composable
fun InicioScreen(
    navController: NavHostController,
<<<<<<< Updated upstream
    productosList: List<ProductoSimple>,   // <-- AHORA SE RECIBE DESDE MainActivity
    nombreUsuario: String = "Nombre de Usuario"
) {
=======
    nombreUsuario: String = "Usuario Noni"
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
>>>>>>> Stashed changes

    var searchQuery by remember { mutableStateOf("") }

    val categorias = listOf("Popular", "Mujer", "Hombre", "Bebé", "Niños y Niñas")
    var seleccionCategoria by remember { mutableStateOf("Popular") }

    val promoReal = listOf(
        R.drawable.promo1,
        R.drawable.promo2,
        R.drawable.promo3
    )

<<<<<<< Updated upstream
    val promoLoop = remember { List(1000) { promoReal[it % promoReal.size] } }
    val startIndex = promoLoop.size / 2
    val carouselState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
=======
    val productosPorCategoria = remember {
        mapOf(
            "Popular" to (1..10).map { ProductoSimple(it, "Popular $it", Random.nextInt(100, 901), getDrawableRes(context, "popular$it")) },
            "Mujer" to (1..10).map { ProductoSimple(100 + it, "Mujer $it", Random.nextInt(100, 901), getDrawableRes(context, "mujer$it")) },
            "Hombre" to (1..10).map { ProductoSimple(200 + it, "Hombre $it", Random.nextInt(100, 901), getDrawableRes(context, "hombre$it")) },
            "Bebé" to (1..10).map { ProductoSimple(300 + it, "Bebé $it", Random.nextInt(100, 901), getDrawableRes(context, "bebe$it")) },
            "Niños y Niñas" to (1..10).map { ProductoSimple(400 + it, "Niños $it", Random.nextInt(100, 901), getDrawableRes(context, "ninos$it")) }
        )
    }
>>>>>>> Stashed changes

    // 👇 AHORA productos vienen de MainActivity
    val productos = productosList

    // Auto Scroll
    LaunchedEffect(Unit) {
<<<<<<< Updated upstream
=======
        InicioData.productosGlobal = productosPorCategoria.values.flatten()
    }

    val priceRanges = listOf("Todos", "100 - 200", "200 - 300", "300 - 400", "400 - 500", "500 - 700", "700 - 900")
    var filtersExpanded by remember { mutableStateOf(false) }
    var selectedRangeLabel by remember { mutableStateOf("Todos") }

    val productosCategoria = productosPorCategoria[seleccionCategoria] ?: emptyList()
    val productosFiltrados = productosCategoria.filter {
        (searchSubmitted.isBlank() || it.nombre.contains(searchSubmitted, ignoreCase = true))
    }

    val promo = promoPorCategoria[seleccionCategoria] ?: emptyList()
    val promoLoop = remember(seleccionCategoria) { List(100) { promo[it % promo.size] } }
    val carouselState = rememberLazyListState(promoLoop.size / 2)

    LaunchedEffect(seleccionCategoria) {
        carouselState.scrollToItem(promoLoop.size / 2)
>>>>>>> Stashed changes
        while (isActive) {
            delay(3000)
            val next = carouselState.firstVisibleItemIndex + 1
            carouselState.animateScrollToItem(next)
            if (next >= promoLoop.size - promoReal.size) carouselState.scrollToItem(startIndex)
        }
    }

    Column(
<<<<<<< Updated upstream
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
=======
        modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)
    ) {
        Spacer(Modifier.height(35.dp))

        // HEADER
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1f).clickable { navController.navigate("perfil") }) {
                Image(painter = painterResource(id = R.drawable.avatar_placeholder), contentDescription = null, modifier = Modifier.size(56.dp).clip(CircleShape))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Bienvenido,", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Text(nombreUsuario, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
            IconButton(onClick = { navController.navigate("carrito") }) {
                Icon(painter = painterResource(id = R.drawable.carrito), contentDescription = null, tint = Color.Black)
>>>>>>> Stashed changes
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ===========================
        // BUSCADOR
<<<<<<< Updated upstream
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
=======
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it; if (it.isBlank()) searchSubmitted = "" },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Buscar...", color = Color.Gray) },
                textStyle = TextStyle(color = Color.Black),
                leadingIcon = { Icon(painterResource(id = R.drawable.lupa), contentDescription = null, tint = Color.Black) },
                trailingIcon = { IconButton(onClick = { searchSubmitted = searchQuery; focusManager.clearFocus() }) { Icon(painterResource(id = R.drawable.lupa), contentDescription = "Buscar", tint = Color.Black) } },
                singleLine = true,
                // Material 2 usa colors diferente, pero OutlinedTextField es compatible
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Gray
                )
            )
            Spacer(Modifier.width(8.dp))
            Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(10.dp)).background(Color(0xFFEDEDED)).clickable { filtersExpanded = true }, contentAlignment = Alignment.Center) {
                Icon(painterResource(id = R.drawable.filtros), contentDescription = null, tint = Color.Black)
                DropdownMenu(
                    expanded = filtersExpanded,
                    onDismissRequest = { filtersExpanded = false },
                    modifier = Modifier.background(Color.White)
                    // En Material 2 NO existe containerColor, se usa modifier.background
                ) {
                    priceRanges.forEach { label ->
                        DropdownMenuItem(onClick = { selectedRangeLabel = label; filtersExpanded = false }) {
                            Text(label, color = Color.Black)
                        }
                    }
                }
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
                    onClick = { seleccionCategoria = cat },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (seleccionCategoria == cat) Color.Black else Color(0xFFEDEDED)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
=======
                    onClick = { seleccionCategoria = cat; searchQuery = ""; searchSubmitted = ""; selectedRangeLabel = "Todos" },
                    shape = RoundedCornerShape(12.dp),
                    // CAMBIO A MATERIAL 2: backgroundColor en lugar de containerColor
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (seleccionCategoria == cat) Color.Black else Color(0xFFEDEDED),
                        contentColor = if (seleccionCategoria == cat) Color.White else Color.Black
                    )
>>>>>>> Stashed changes
                ) {
                    Text(cat)
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
<<<<<<< Updated upstream

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
=======
            item(span = { GridItemSpan(2) }) {
                LazyRow(state = carouselState, modifier = Modifier.height(170.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(promoLoop) { img ->
                        Image(painterResource(id = img), contentDescription = null, modifier = Modifier.width(320.dp).fillMaxHeight().clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
                    }
                }
            }
            items(items = productosFiltrados, key = { it.id }) { prod ->
                val esFavorito = remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier.height(220.dp).clickable { navController.navigate("com.example.appnony.producto/${prod.id}/${prod.nombre}/${prod.precio}/${prod.imagenRes}") },
                    shape = RoundedCornerShape(12.dp),
                    // CAMBIO A MATERIAL 2: backgroundColor
                    backgroundColor = Color(0xFFF3F3F3),
                    elevation = 0.dp
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Image(painterResource(id = prod.imagenRes), contentDescription = null, modifier = Modifier.height(130.dp).fillMaxWidth().clip(RoundedCornerShape(10.dp)), contentScale = ContentScale.Crop)
                        Spacer(Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text(prod.nombre, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text("$${prod.precio}", fontWeight = FontWeight.SemiBold, color = Color.Black)
                            }
                            Icon(painterResource(id = if (esFavorito.value) R.drawable.corazonrelleno else R.drawable.corazon), contentDescription = null, tint = if (esFavorito.value) Color.Red else Color.Black, modifier = Modifier.size(24.dp).clickable { esFavorito.value = !esFavorito.value })
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
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

=======
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            BottomIcon(R.drawable.inicio) {}
>>>>>>> Stashed changes
            BottomIcon(R.drawable.corazon) { navController.navigate("favoritos") }

            BottomIcon(R.drawable.carrito) { navController.navigate("carrito") }

            BottomIcon(R.drawable.perfil) { navController.navigate("perfil") }
        }
    }
}

@Composable
<<<<<<< Updated upstream
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
=======
fun BottomIcon(icon: Int, onClick: () -> Unit) {
    Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFEDEDED)).clickable { onClick() }, contentAlignment = Alignment.Center) {
        Icon(painterResource(id = icon), contentDescription = null, tint = Color.Black)
>>>>>>> Stashed changes
    }
}