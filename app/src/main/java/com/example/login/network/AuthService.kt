package com.example.login.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    // LOGIN real con DummyJSON
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // REGISTRO simulado (DummyJSON no lo soporta)
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse> {
        // Simulamos como si se hubiera registrado exitosamente
        val fakeResponse = RegisterResponse(
            id = 999,
            username = request.username,
            email = request.email,
            message = "Usuario registrado (simulado)"
        )
        return Response.success(fakeResponse)
    }
}
