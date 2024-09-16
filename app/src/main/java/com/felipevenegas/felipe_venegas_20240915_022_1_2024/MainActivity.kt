package com.felipevenegas.felipe_venegas_20240915_022_1_2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.felipevenegas.felipe_venegas_20240915_022_1_2024.composable.ListaJuguetes
import com.felipevenegas.felipe_venegas_20240915_022_1_2024.model.Juguete
import com.felipevenegas.felipe_venegas_20240915_022_1_2024.ui.theme.FelipeVenegasJUEGAMTheme
import kotlinx.serialization.Serializable
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FelipeVenegasJUEGAMTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        val navController = rememberNavController()

                        var listaJuguetes = remember { mutableStateListOf<Juguete>() }

                        if (listaJuguetes.isEmpty()) {
                            for (i in 0..9) {
                                listaJuguetes.add(Juguete(i, "Juguete ${i+1}", getDrawableForToy(i), 1990*i))
                            }
                        }

                        Toolbar(navController)

                        NavHost(navController = navController, startDestination = Lista){
                            composable<Lista> {
                                ListaJuguetes ({
                                    navController.navigate(it)
                                }, listaJuguetes)
                            }
                            composable<Detalle> {
                                val args = it.toRoute<Detalle>()
                                DetalleJuguete(item = Juguete(args.id, args.nombre, args.imagen, args.precio))
                            }
                        }

                    }
                }
            }
        }
    }
}

@Serializable
object Lista

@Serializable
data class Detalle(
    val id: Int,
    val nombre: String,
    val precio: Int,
    @DrawableRes
    val imagen: Int,
)

@Composable
fun Toolbar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF6200EE))
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                if (!navController.popBackStack()) {
                    finish()
                }
            }
        ){
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Settings")
        }

        Text(
            text = stringResource(R.string.titulo),
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

fun finish() {
    exitProcess(0)
}

private fun getDrawableForToy(index: Int): Int {
    return when (index) {
        0 -> R.drawable.toy1
        1 -> R.drawable.toy2
        2 -> R.drawable.toy3
        3 -> R.drawable.toy4
        4 -> R.drawable.toy5
        5 -> R.drawable.toy6
        6 -> R.drawable.toy7
        7 -> R.drawable.toy8
        8 -> R.drawable.toy9
        9 -> R.drawable.toy10
        else -> R.drawable.placeholder // Default drawable
    }
}