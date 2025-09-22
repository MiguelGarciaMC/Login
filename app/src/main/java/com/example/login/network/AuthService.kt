package com.example.login.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//Modelo de request
data class LoginRequest(
    val username: String,
    val password: String
)

//Modelo de response
data class LoginResponse(
    val token: String
)

interface AuthService {
    @POST("login") //remplazar por el API
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}