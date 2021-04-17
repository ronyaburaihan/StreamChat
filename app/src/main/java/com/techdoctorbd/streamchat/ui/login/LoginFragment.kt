package com.techdoctorbd.streamchat.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.techdoctorbd.streamchat.databinding.FragmentLoginBinding
import com.techdoctorbd.streamchat.models.ChatUser

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

        binding.button.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString().trim()
            val username = binding.usernameEditText.text.toString().trim()

            if (validateInput(firstName, binding.firstNameInputLayout) &&
                validateInput(username, binding.usernameInputLayout)
            ) {
                val chatUser = ChatUser(firstName, username)
                val action = LoginFragmentDirections.actionLoginFragmentToChannelFragment(chatUser)
                findNavController().navigate(action)
            }
        }
    }

    private fun validateInput(inputText: String, textInputLayout: TextInputLayout): Boolean {
        return if (inputText.length < 4) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = "Minimum 4 characters required"
            false
        } else {
            textInputLayout.isErrorEnabled = false
            textInputLayout.error = null
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}