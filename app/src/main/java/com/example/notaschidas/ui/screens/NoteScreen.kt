package com.example.notaschidas.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.notaschidas.data.model.Note
import com.example.notaschidas.utils.GyroShakeDetector
import com.example.notaschidas.viewmodel.NoteViewModel

@Composable
fun NoteScreen(
    viewModel: NoteViewModel,
    onAgregar: () -> Unit,
    onEditar: (Int) -> Unit
) {
    val notas by viewModel.notas.collectAsState()
    var notaSeleccionada by remember { mutableStateOf<Note?>(null) }
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val giroscopio = remember {
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    val gyroDetector = remember {
        GyroShakeDetector {
            if (notaSeleccionada != null) {
                mostrarConfirmacion = true
            }
        }
    }

    DisposableEffect(Unit) {
        if (giroscopio != null) {
            sensorManager.registerListener(
                gyroDetector,
                giroscopio,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        onDispose {
            sensorManager.unregisterListener(gyroDetector)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregar) {
                Text("+")
            }
        }
    ) { padding ->

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(notas) { nota ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            notaSeleccionada = nota
                        }
                ) {
                    Column(Modifier.padding(12.dp)) {

                        Text(nota.titulo, fontWeight = FontWeight.Bold)
                        Text(nota.descripcion)

                        nota.imageUri?.let {
                            AsyncImage(
                                model = it,   // ahora es URL REMOTA
                                contentDescription = null
                            )
                        }

                        Row {
                            Button(onClick = {
                                notaSeleccionada = nota
                                onEditar(nota.id)
                            }) {
                                Text("Editar")
                            }
                        }
                    }
                }
            }
        }
    }

    //CONFIRMACIÓN CON GIROSCOPIO
    if (mostrarConfirmacion && notaSeleccionada != null) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("Eliminar nota") },
            text = { Text("Se detectó un giro brusco del dispositivo.\n¿Deseas eliminar esta nota?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.borrar(notaSeleccionada!!.id)
                    notaSeleccionada = null
                    mostrarConfirmacion = false
                }) { Text("Sí, borrar") }
            },
            dismissButton = {
                Button(onClick = { mostrarConfirmacion = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}