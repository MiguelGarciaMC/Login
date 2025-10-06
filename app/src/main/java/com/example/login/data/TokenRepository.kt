package com.example.login.data

import android.content.Context
import com.example.login.data.local.AppDbHelper
import com.example.login.data.local.TokenDao
import com.example.login.data.local.TokenDaoImpl
import com.example.login.data.local.TokenEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class TokenRepository private constructor(context: Context) {

    private val dbHelper = AppDbHelper(context.applicationContext)
    private val dao: TokenDao = TokenDaoImpl(dbHelper)

    // Observabilidad simple: cache + StateFlow
    private val mutableTokenFlow = MutableStateFlow<String?>(null)
    val tokenFlow: StateFlow<String?> = mutableTokenFlow.asStateFlow()

    // Inicializa el flow con el valor actual (llamar una vez desde la VM si quieres tenerlo al arrancar)
    suspend fun init() = withContext(Dispatchers.IO) {
        mutableTokenFlow.value = dao.getTokenOnce()
    }

    // Equivalentes públicos a lo que tenías:
    fun observeToken(): Flow<String?> = tokenFlow

    suspend fun getTokenOnce(): String? = withContext(Dispatchers.IO) {
        dao.getTokenOnce()
    }

    suspend fun saveToken(token: String, firstName: String) = withContext(Dispatchers.IO) {
        dao.upsert(TokenEntity(token = token, firstName = firstName))
        // Actualiza el flow
        mutableTokenFlow.value = token
    }

    suspend fun getUser(): TokenEntity? = withContext(Dispatchers.IO) {
        dao.getUser()
    }

    suspend fun clearToken() = withContext(Dispatchers.IO) {
        dao.clear()
        mutableTokenFlow.value = null
    }

    companion object {
        @Volatile private var INSTANCE: TokenRepository? = null

        fun getInstance(context: Context): TokenRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TokenRepository(context).also { INSTANCE = it }
            }
    }
}
