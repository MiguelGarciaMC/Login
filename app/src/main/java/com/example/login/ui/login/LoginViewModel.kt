package com.example.login.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.data.auth.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val authRepo = AuthRepository.getInstance(app)

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
}
