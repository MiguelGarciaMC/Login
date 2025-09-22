package com.example.login.network

data class LoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val token: String
)
