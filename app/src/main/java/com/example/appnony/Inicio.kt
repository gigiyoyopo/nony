package com.example.appnony

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.random.Random

data class ProductoSimple(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val imagenRes: Int
)

@Composable
fun InicioScreen(
    navController: NavHostController,
    nombreUsuario: String = "Nombre de Usuario"
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var searchQuery by remember { mutableStateOf("") }
    var searchSubmitted by remember { mutableStateOf("") }
    var seleccionCategoria by remember { mutableStateOf("Popular") }

    val categorias = listOf("Popular", "Mujer", "Hombre", "Bebé", "Niños y Niñas")

    val promoPorCategoria = mapOf(
        "Popular" to listOf(R.drawable.popularpromo1, R.drawable.popularpromo2),
        "Mujer" to listOf(R.drawable.mujerpromo1, R.drawable.mujerpromo2),
        "Hombre" to listOf(R.drawable.hombrepromo1, R.drawable.hombrepromo2),
        "Bebé" to listOf(R.drawable.bebepromo1, R.drawable.bebepromo2),
        "Niños y Niñas" to listOf(R.drawable.ninospromo1, R.drawable.ninospromo2)
    )

    val productosPorCategoria = remember {
        mapOf(
            "Popular" to (1..10).map {
                ProductoSimple(
                    id = it,
                    nombre = "Popular $it",
                    precio = Random.nextInt(100, 901),
                    imagenRes = getDrawableRes(context, "popular$it")
                )
            },
            "Mujer" to (1..10).map {
                ProductoSimple(
                    id = 100 + it,
                    nombre = "Mujer $it",
                    precio = Random.nextInt(100, 901),
                    imagenRes = getDrawableRes(context, "mujer$it")
                )
            },
            "Hombre" to (1..10).map {
                ProductoSimple(
                    id = 200 + it,
                    nombre = "Hombre $it",
                    precio = Random.nextInt(100, 901),
                    imagenRes = getDrawableRes(context, "hombre$it")
                )
            },
            "Bebé" to (1..10).map {
                ProductoSimple(
                    id = 300 + it,
                    nombre = "Bebé $it",
                    precio = Random.nextInt(100, 901),
                    imagenRes = getDrawableRes(context, "bebe$it")
                )
            },
            "Niños y Niñas" to (1..10).map {
                ProductoSimple(
                    id = 400 + it,
                    nombre = "Niños $it",
                    precio = Random.nextInt(100, 901),
                    imagenRes = getDrawableRes(context, "ninos$it")
                )
            }
        )
    }

    LaunchedEffect(Unit) {
        InicioData.productosGlobal = productosPorCategoria.values.flatten()
    }

    val priceRanges = listOf(
        "Todos" to (0 to 10000),
        "100 - 200" to (100 to 200),
        "200 - 300" to (200 to 300),
        "300 - 400" to (300 to 400),
        "400 - 500" to (400 to 500),
        "500 - 700" to (500 to 700),
        "700 - 900" to (700 to 900)
    )

    var filtersExpanded by remember { mutableStateOf(false) }
    var selectedRangeLabel by remember { mutableStateOf("Todos") }
    val selectedRange = priceRanges.first { it.first == selectedRangeLabel }.second

    val productosCategoria = productosPorCategoria[seleccionCategoria]!!

    val productosFiltrados by remember(seleccionCategoria, searchSubmitted, selectedRangeLabel) {
        derivedStateOf {
            productosCategoria
                .filter { it.precio in selectedRange.first..selectedRange.second }
                .filter {
                    if (searchSubmitted.isBlank()) true
                    else it.nombre.lowercase().contains(searchSubmitted.lowercase())
                }
        }
    }

    val promo = promoPorCategoria[seleccionCategoria] ?: emptyList()
    val promoLoop = remember(seleccionCategoria) {
        List(1000) { promo[it % promo.size] }
    }
    val carouselState = rememberLazyListState(promoLoop.size / 2)

    LaunchedEffect(seleccionCategoria) {
        carouselState.scrollToItem(promoLoop.size / 2)
    }

    LaunchedEffect(seleccionCategoria) {
        while (isActive) {
            delay(3000)
            carouselState.animateScrollToItem(carouselState.firstVisibleItemIndex + 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        Spacer(Modifier.height(35.dp))

        // HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f)
                    .clickable { navController.navigate("perfil") }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        "Bienvenido,",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Text(
                        nombreUsuario,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            IconButton(onClick = { navController.navigate("carrito") }) {
                Icon(
                    painter = painterResource(id = R.drawable.carrito),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // BUSCADOR
        Row(verticalAlignment = Alignment.CenterVertically) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    if (it.isBlank()) searchSubmitted = ""
                },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Buscar...", color = Color.Gray) },
                textStyle = TextStyle(color = Color.Black),
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.lupa),
                        contentDescription = null,
                        tint = Color.Black
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        searchSubmitted = searchQuery
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            painterResource(id = R.drawable.lupa),
                            contentDescription = "Buscar",
                            tint = Color.Black
                        )
                    }
                },
                singleLine = true
            )

            Spacer(Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFEDEDED))
                    .clickable { filtersExpanded = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(id = R.drawable.filtros),
                    contentDescription = null,
                    tint = Color.Black
                )

                DropdownMenu(
                    expanded = filtersExpanded,
                    onDismissRequest = { filtersExpanded = false },
                    modifier = Modifier.background(Color.White),
                    containerColor = Color.White
                ) {
                    priceRanges.forEach { (label, _) ->
                        DropdownMenuItem(
                            text = { Text(label, color = Color.Black) },
                            onClick = {
                                selectedRangeLabel = label
                                filtersExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // CATEGORÍAS
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categorias) { cat ->
                Button(
                    onClick = {
                        seleccionCategoria = cat
                        searchQuery = ""
                        searchSubmitted = ""
                        selectedRangeLabel = "Todos"
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (seleccionCategoria == cat)
                            Color.Black else Color(0xFFEDEDED)
                    )
                ) {
                    Text(
                        cat,
                        color = if (seleccionCategoria == cat) Color.White else Color.Black
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // GRID
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {

            // PROMOS
            item(span = { GridItemSpan(2) }) {
                LazyRow(
                    state = carouselState,
                    modifier = Modifier
                        .height(170.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(promoLoop) { img ->
                        Image(
                            painterResource(id = img),
                            contentDescription = null,
                            modifier = Modifier
                                .width(320.dp)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            // PRODUCTOS
            items(
                items = productosFiltrados,
                key = { it.id }   // ← SOLUCIÓN OFICIAL PARA QUE NO HEREDEN "FAVORITO"
            ) { prod ->

                val esFavorito by remember {
                    derivedStateOf { FavoritosState.isFavorite(prod.id) }
                }

                Card(
                    modifier = Modifier
                        .height(220.dp)
                        .clickable {
                            navController.navigate(
                                "producto/${prod.id}/${prod.nombre}/${prod.precio}/${prod.imagenRes}"
                            )
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF3F3F3)
                    )
                ) {
                    Column(Modifier.padding(8.dp)) {

                        Image(
                            painterResource(id = prod.imagenRes),
                            contentDescription = null,
                            modifier = Modifier
                                .height(130.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(prod.nombre, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text("$${prod.precio}", fontWeight = FontWeight.SemiBold, color = Color.Black)
                            }

                            Icon(
                                painterResource(
                                    id = if (esFavorito) R.drawable.corazonrelleno
                                    else R.drawable.corazon
                                ),
                                contentDescription = null,
                                tint = if (esFavorito) Color.Red else Color.Black,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { FavoritosState.toggle(prod.id) }
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // BOTTOM BAR
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomIcon(R.drawable.inicio) {}
            BottomIcon(R.drawable.corazon) { navController.navigate("favoritos") }
            BottomIcon(R.drawable.carrito) { navController.navigate("carrito") }
            BottomIcon(R.drawable.perfil) { navController.navigate("perfil") }
        }
    }
}

fun getDrawableRes(context: Context, name: String): Int {
    val res = context.resources.getIdentifier(name.lowercase(), "drawable", context.packageName)
    return if (res != 0) res else R.drawable.error_placeholder
}

@Composable
fun BottomIcon(icon: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEDEDED))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(id = icon),
            contentDescription = null,
            tint = Color.Black
        )
    }
}
