package com.example.login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.login.databinding.FragmentLoginBinding
import com.example.login.R
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import android.widget.Toast
import kotlinx.coroutines.launch
import com.example.login.data.TokenRepository

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.isEnabled = true
        binding.login.setOnClickListener {
            viewModel.simulateLoginAndSave(
                onSaved = {
                    findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
                },
                onError = { e ->
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }


    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            val token = TokenRepository.getInstance(requireContext()).getTokenOnce()
            if (!token.isNullOrBlank()) {
                findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)

            }
        }
    }
}
