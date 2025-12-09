package com.example.notaschidas.data.network

import com.example.notaschidas.data.model.Note

interface ApiService {
    @GET("notes")
    suspend fun getNotes(): List<Note>

    @Multipart
    @POST("notes")
    suspend fun createNote(
        @Part("titulo") titulo: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @part image: MultipartBody.Part?
    ): Note

    @Multipart
    @PUT("notes/{id}")
    suspend fun updateNote(
        @Path("id") id: Int,
        @Part("titulo") titulo: ResquestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part?
    ): Note

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: Int)
}