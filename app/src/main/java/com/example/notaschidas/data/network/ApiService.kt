package com.example.notaschidas.data.network

import com.example.notaschidas.data.model.Note
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

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
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part?
    ): Note

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: Int)
}