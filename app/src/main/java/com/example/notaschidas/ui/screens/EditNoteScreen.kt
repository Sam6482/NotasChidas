package com.example.notaschidas.ui.screens

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

    // ✅ ARCHIVO REAL PARA LA CÁMARA
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

    // ✅ GALERÍA
    val launcherGaleria = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        imagenUri = uri?.toString()
    }

    // ✅ CÁMARA REAL
    val launcherCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imagenUri = fotoUri.toString()
        }
    }

    // ✅ PERMISO CÁMARA
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

        // ✅ BOTÓN QUE AHORA SÍ FUNCIONA
        Button(onClick = { mostrarOpciones = true }) {
            Text("Seleccionar Imagen")
        }

        // ✅ PREVISUALIZACIÓN
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

        // ✅ SELECTOR CÁMARA / GALERÍA
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
