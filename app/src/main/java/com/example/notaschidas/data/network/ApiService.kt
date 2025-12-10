package com.example.notaschidas.data.network

import com.example.notaschidas.data.model.Note
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @GET("notes")
    suspend fun getNotes(): List<Note>

    @Multipart
    @POST("notes")
    suspend fun createNote(
        @Part("titulo") titulo: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part image: MultipartBody.Part?
    ): Note

    @Multipart
    @PUT("notes/{id}")
    suspend fun updateNote(
        @Path("id") id: Int,
        @Part("titulo") titulo: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part image: MultipartBody.Part?
    ): Note

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: Int)
}