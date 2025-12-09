package com.example.notaschidas.viewmodel

class NoteViewModel : ViewModel() {

    private val _notas = MutableStateFlow<List<Note>>(emptyList())
    val notas: StateFlow<List<Note>> = _notas

    init {
        cargarNotas()
    }

    fun cargarNotas() {
        viewModelScope.launch {
            try {
                val notasApi = RetrofitClient.api.getNotes()
                _notas.value = notasApi
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertar(context: Context, titulo: String, descripcion: String, imageUri: Uri?) {
        viewModelScope.launch {
            try {
                val tituloBody = titulo.toRequestBody("text/plain".toMediaType())
                val descripcionBody = descripcion.toRequestBody("text/plain".toMediaType())

                var imagePart: MultipartBody.Part? = null

                imageUri?.let {
                    val file = mx.edu.utez.notaschidas.utils.uriToFile(context, it)
                    val requestFile = file.asRequestBody("image/*".toMediaType())

                    imagePart = MultipartBody.Part.createFormData(
                        "image",
                        file.name,
                        requestFile
                    )
                }

                RetrofitClient.api.createNote(
                    tituloBody,
                    descripcionBody,
                    imagePart
                )

                cargarNotas()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun borrar(id: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.api.deleteNote(id)
                cargarNotas()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun actualizar(
        context: Context,
        id: Int,
        titulo: String,
        descripcion: String,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            val tituloBody = titulo.toRequestBody("text/plain".toMediaType())
            val descripcionBody = descripcion.toRequestBody("text/plain".toMediaType())

            var imagePart: MultipartBody.Part? = null

            imageUri?.let {
                val file = mx.edu.utez.notaschidas.utils.uriToFile(context, it)
                val requestFile = file.asRequestBody("image/*".toMediaType())

                imagePart = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestFile
                )
            }

            RetrofitClient.api.updateNote(id, tituloBody, descripcionBody, imagePart)
            cargarNotas()
        }
    }
}