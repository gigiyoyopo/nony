package com.example.appnony

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appnony.ui.theme.AppnonyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppnonyTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController: NavHostController = rememberNavController()

    // ⭐ LISTA GLOBAL DE PRODUCTOS
    val productos = remember {
        List(20) { index ->
            ProductoSimple(
                id = index,
                nombre = "Producto ${index + 1}",
                precio = "$${100 + index * 5}.00",
                imagenRes = R.drawable.producto_placeholder
            )
        }
    }

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {

        // 🏠 Inicio
        composable("inicio") {
            InicioScreen(
                navController = navController,
                productosList = productos              // <-- ahora se envía aquí
            )
        }

        // 🛒 Carrito
        composable("carrito") {
            CarritoScreen(navController = navController)
        }

        // ❤️ Favoritos (Lista filtrada en tiempo real)
        composable("favoritos") {

            val favoritos = FavoritosState.getFavoritosList(productos)

            FavoritosScreen(
                navController = navController,
                productosList = favoritos              // <-- favoritos listos
            )
        }

        // 👤 Perfil
        composable("perfil") {
            PerfilScreen(navController)
        }

        // 🔔 Notificaciones
        composable("notificaciones") {
            NotificacionesScreen(navController)
        }

        // 📦 Producto
        composable(
            route = "producto/{id}/{nombre}/{precio}/{imagenRes}"
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            val imagenRes = backStackEntry.arguments?.getString("imagenRes")?.toInt() ?: 0

            ProductoScreen(
                navController = navController,
                id = id,
                nombre = nombre,
                precio = precio,
                imagenRes = imagenRes
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    AppnonyTheme {
        AppNavigation()
    }
}