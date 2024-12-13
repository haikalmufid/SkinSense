package com.capstone.skinsense.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.capstone.skinsense.data.response.User
import com.capstone.skinsense.databinding.ActivityLoginBinding
import com.capstone.skinsense.ui.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        loginViewModel.loginResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                navigateToMainActivity()
            }
        })

        loginViewModel.errorMessage.observe(this, Observer { message ->
            showError(message)
        })
    }

    private fun setupListeners() {
        binding.BtnSignIn.setOnClickListener {
            val email = binding.SignInEmail.text.toString()
            val password = binding.SignInPassword.text.toString()

            loginViewModel.handleLogin(email, password)
        }

        binding.BtnSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
