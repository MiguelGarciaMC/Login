package com.example.login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.login.R
import com.example.login.databinding.FragmentLoginBinding
import com.example.login.network.ApiClient
import com.example.login.network.LoginRequest
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Botón de login
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Toast.makeText(requireContext(), "Completa los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón de registro
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun loginUser(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.retrofitService.login(LoginRequest(username, password))

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token  // Asegúrate de que tu LoginResponse tenga 'token'

                    // Guardar token en SharedPreferences
                    val prefs = requireContext().getSharedPreferences("app_prefs", 0)
                    prefs.edit().putString("auth_token", token).apply()

                    // Navegar a WelcomeFragment
                    findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
                } else {
                    Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
