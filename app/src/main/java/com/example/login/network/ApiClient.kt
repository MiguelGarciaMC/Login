package com.example.login.network

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://api.com/" //Aqui se pondrá la url del back

    val instance: AuthService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //Convierte a JSON
            .build()

        retrofit.create(AuthService::class.java)
    }
}