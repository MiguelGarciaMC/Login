package com.example.login.data.auth

import android.content.Context
import com.example.login.data.TokenRepository
import com.example.login.network.ApiClient
import com.example.login.network.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository private constructor(context: Context) {

    private val tokenRepo = TokenRepository.getInstance(context)

    /**
     * Llama a DummyJSON, valida la respuesta y guarda el accessToken en Room.
     * Devuelve el accessToken guardado.
     */
    suspend fun loginAndPersist(username: String, password: String): String = withContext(Dispatchers.IO) {
        val response = ApiClient.retrofitService.login(LoginRequest(username, password))
        if (!response.isSuccessful) {
            val msg = response.errorBody()?.string()?.take(300) ?: "Login inválido"
            throw IllegalStateException("Error ${response.code()}: $msg")
        }
        val body = response.body() ?: throw IllegalStateException("Respuesta vacía")
        tokenRepo.saveToken(body.accessToken, body.firstName)
        body.accessToken
    }

    companion object {
        @Volatile private var INSTANCE: AuthRepository? = null
        fun getInstance(context: Context): AuthRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthRepository(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
