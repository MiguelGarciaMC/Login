package com.example.login.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.data.TokenRepository
import com.example.login.data.auth.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val authRepo = AuthRepository.getInstance(app)
    private val tokenRepo = TokenRepository.getInstance(app)

    val token: StateFlow<String?> =
        tokenRepo.observeToken()
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    init {
        viewModelScope.launch { tokenRepo.init() }
    }

    fun login(
        username: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {

                authRepo.loginAndPersist(username, password)
                onSuccess()
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }

    fun clearSession() {
        viewModelScope.launch { tokenRepo.clearToken() }
    }
}
