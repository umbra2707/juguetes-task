package com.felipevenegas.felipe_venegas_20240915_022_1_2024.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.felipevenegas.felipe_venegas_20240915_022_1_2024.Detalle
import com.felipevenegas.felipe_venegas_20240915_022_1_2024.model.Juguete
import com.felipevenegas.felipe_venegas_20240915_022_1_2024.model.MenuContextual

@Composable
fun ListaJuguetes(navegarAlDetalle:(Detalle) -> Unit, listaJuguetes: MutableList<Juguete>){

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(items = listaJuguetes, key = { it.id }){
                ItemJuguete(
                    item = it,
                    itemsMenuContextual = listOf(
                        MenuContextual("Eliminar")
                    ),
                    onItemClick = { id ->
                        var jugueteAEliminar = Juguete(0,"",0,0)

                        for (juguete in listaJuguetes){
                            if (juguete.id == id){
                                jugueteAEliminar = juguete
                            }
                        }

                        listaJuguetes.remove(jugueteAEliminar)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navegarAlDetalle = navegarAlDetalle
                )
            }
        }
    }
}

@Composable
fun ItemJuguete(
    item: Juguete,
    itemsMenuContextual: List<MenuContextual>,
    modifier: Modifier,
    onItemClick: (Int) -> Unit,
    navegarAlDetalle:(Detalle) -> Unit) {

    var menuContextualVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var offsetDespliegue by remember {
        mutableStateOf(DpOffset.Zero)
    }

    var alturaItem by remember {
        mutableStateOf(0.dp)
    }

    val interaccion = remember {
        MutableInteractionSource()
    }

    val densidad = LocalDensity.current

    Row (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Card (
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            modifier = modifier.onSizeChanged  { alturaItem = with(densidad) { it.height.toDp() } }
        ) {
            Text(text = item.nombre, modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .indication(interaccion, LocalIndication.current)
                    .pointerInput(true) {
                        detectTapGestures(
                            onLongPress = {
                                menuContextualVisible = true
                                offsetDespliegue = DpOffset(it.x.toDp(), it.y.toDp())
                            },
                            onPress = {
                                val press = PressInteraction.Press(it)
                                interaccion.emit(press)
                                interaccion.emit(PressInteraction.Release(press))

                                val debeNavegar = tryAwaitRelease()

                                if (debeNavegar && !menuContextualVisible) {
                                    navegarAlDetalle(Detalle(id = item.id, nombre = item.nombre, precio = item.precio, imagen = item.imagen))
                                }
                            }
                        )
                    }
            ) {
                Image(painter = painterResource(id = item.imagen), contentDescription = item.nombre)
            }
            DropdownMenu(
                expanded = menuContextualVisible,
                onDismissRequest = {
                    menuContextualVisible = false
                },
                offset = offsetDespliegue.copy(
                    y = offsetDespliegue.y - alturaItem
                )
            ) {
                itemsMenuContextual.forEach {
                    DropdownMenuItem(text = {
                        Text(text = it.texto, modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally))
                    }, onClick = {
                        onItemClick(item.id)
                        menuContextualVisible = false
                    })
                }
            }
        }
    }
}