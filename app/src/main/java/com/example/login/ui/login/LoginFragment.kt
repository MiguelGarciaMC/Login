package com.example.login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.login.R
import com.example.login.data.TokenRepository
import com.example.login.databinding.FragmentLoginBinding
import com.example.login.network.ApiClient
import com.example.login.network.LoginRequest
import com.example.login.network.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Toast.makeText(requireContext(), "Completa los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val response: Response<LoginResponse> =
                    ApiClient.retrofitService.login(LoginRequest(username, password))

                if (response.isSuccessful && response.body() != null) {
                    val loginData = response.body()!!

                    // Guarda token en DB/Room
                    TokenRepository.getInstance(requireContext()).saveToken(loginData.accessToken)

                    // Pasa el firstName al WelcomeFragment
                    val bundle = Bundle().apply {
                        putString("FIRST_NAME", loginData.firstName)
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment, bundle)

                } else {
                    Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Auto-login si ya hay token
        lifecycleScope.launch {
            val token = TokenRepository.getInstance(requireContext()).getTokenOnce()
            if (!token.isNullOrBlank()) {
                val bundle = Bundle().apply {
                    putString("FIRST_NAME", "Usuario")
                }
                findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}