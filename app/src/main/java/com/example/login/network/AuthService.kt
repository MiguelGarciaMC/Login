package com.example.login.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

//Modelo de request
data class LoginRequest(
    val username: String,
    val password: String,
    val expiresInMins: Int? = null
)

//Modelo de response
data class LoginResponse(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val image: String,
    val accessToken: String,
    val refreshToken: String
)

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}