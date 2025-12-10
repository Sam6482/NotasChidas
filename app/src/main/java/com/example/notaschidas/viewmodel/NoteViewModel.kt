package com.example.notaschidas.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notaschidas.data.model.Note
import com.example.notaschidas.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody



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
                    val file = com.example.notaschidas.utils.uriToFile(context, it)
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
                val file = com.example.notaschidas.utils.uriToFile(context, it)
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