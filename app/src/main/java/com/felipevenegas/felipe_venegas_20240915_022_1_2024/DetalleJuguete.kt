package com.felipevenegas.felipe_venegas_20240915_022_1_2024

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.felipevenegas.felipe_venegas_20240915_022_1_2024.model.Juguete

@Composable
fun DetalleJuguete(item: Juguete){

    Row (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Card (
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Text(text = item.nombre, modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally))
            Image(painter = painterResource(id = item.imagen), contentDescription = item.nombre)
        }
    }
}