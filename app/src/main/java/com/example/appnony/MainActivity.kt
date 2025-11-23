// --- MainActivity.kt ---
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
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {

        // 🏠 Inicio
        composable("inicio") {
            InicioScreen(navController = navController)
        }

        // 🛒 Carrito
        composable("carrito") {
            CarritoScreen(navController = navController)
        }

        composable("ticket") { TicketScreen(navController) }
        composable("fin") { FinScreen(navController) }

        // ❤️ Favoritos
        composable("favoritos") {
            FavoritosScreen(
                navController = navController,
                productosList = InicioData.productosGlobal
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
            "producto/{id}/{nombre}/{precio}/{imagenRes}"
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val precio = backStackEntry.arguments?.getString("precio")?.toInt() ?: 0
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
    AppnonyTheme { AppNavigation() }
}
