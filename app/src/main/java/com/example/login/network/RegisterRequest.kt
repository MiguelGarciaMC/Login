package com.example.login.network

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String
)