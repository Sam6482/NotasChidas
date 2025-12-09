package com.example.notaschidas.data.network

object RetrofitClient {

    private const val BASE_URL = "https://192.168.1.103:5000/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}