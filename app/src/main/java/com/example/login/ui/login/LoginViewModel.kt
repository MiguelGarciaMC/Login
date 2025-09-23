package com.example.login.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.auth.FakeAuthService
import com.example.login.data.TokenRepository
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = TokenRepository.getInstance(app.applicationContext)

    fun simulateLoginAndSave(onSaved: () -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val token = FakeAuthService.FAKE_TOKEN
                repo.saveToken(token)   // guarda en Room
                onSaved()
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }
}
