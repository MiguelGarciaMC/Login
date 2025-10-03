package com.example.login.data

import android.content.Context
import com.example.login.data.local.TokenDatabase
import com.example.login.data.local.TokenEntity
import kotlinx.coroutines.flow.Flow

class TokenRepository private constructor(context: Context) {

    private val dao = TokenDatabase.getInstance(context).tokenDao()

    fun observeToken(): Flow<String?> = dao.observeToken()

    suspend fun getTokenOnce(): String? = dao.getTokenOnce()

    //Modificado para guardar tanto token como firstName
    suspend fun saveToken(token: String, firstName: String) {
        dao.upsert(TokenEntity(token = token, firstName = firstName))
    }

    //Se agrega el método para obtener el usuario
    suspend fun getUser(): TokenEntity? = dao.getUser()

    suspend fun clearToken() {
        dao.clear()
    }

    companion object {
        @Volatile private var INSTANCE: TokenRepository? = null

        fun getInstance(context: Context): TokenRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TokenRepository(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
