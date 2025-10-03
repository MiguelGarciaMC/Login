package com.example.login.network

data class LoginResponse(
    val id: Int,
    val username: String,
    val firstName: String,
    val accessToken: String
)
