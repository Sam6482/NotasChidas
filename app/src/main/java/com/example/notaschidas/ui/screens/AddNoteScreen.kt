package com.example.notaschidas.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage

import java.io.File



@Composable
fun AddNoteScreen(
    onGuardar: (String, String, Uri?) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<String?>(null) }
    var mostrarOpciones by remember { mutableStateOf(false) }


    val archivoFoto = remember {
        File(context.cacheDir, "foto_${System.currentTimeMillis()}.jpg")
    }

    val fotoUri: Uri = remember {
        FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            archivoFoto
        )
    }


    val launcherGaleria = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        imagenUri = uri?.toString()
    }


    val launcherCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imagenUri = fotoUri.toString()
        }
    }


    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permitido ->
        if (permitido) {
            launcherCamara.launch(fotoUri)
        }
    }

    Column(Modifier.padding(16.dp)) {

        Text("Agregar Nota", Modifier.padding(15.dp),fontWeight = FontWeight.Bold)

        TextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
                .heightIn(200.dp, 600.dp)
        )


        Button(onClick = { mostrarOpciones = true }) {
            Text("Seleccionar Imagen")
        }


        imagenUri?.let {
            AsyncImage(
                model = it,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dp)
                    .padding(top = 8.dp)
            )
        }

        Button(onClick = {
            onGuardar(
                titulo,
                descripcion,
                imagenUri?.let { Uri.parse(it) }
            )
            onBack()
        }) {
            Text("Guardar")
        }


        if (mostrarOpciones) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { mostrarOpciones = false },
                confirmButton = {},
                title = { Text("Seleccionar imagen") },
                text = {
                    Column {

                        Button(onClick = {
                            launcherGaleria.launch("image/*")
                            mostrarOpciones = false
                        }) { Text("Desde Galería") }

                        Button(onClick = {
                            val permiso = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            )

                            if (permiso == PackageManager.PERMISSION_GRANTED) {
                                launcherCamara.launch(fotoUri)
                            } else {
                                permisoCamaraLauncher.launch(Manifest.permission.CAMERA)
                            }

                            mostrarOpciones = false
                        }) { Text("Desde Cámara") }
                    }
                }
            )
        }
    }
}
