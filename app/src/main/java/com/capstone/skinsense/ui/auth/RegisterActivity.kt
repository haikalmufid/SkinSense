package com.capstone.skinsense.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.skinsense.data.response.User
import com.capstone.skinsense.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUI()
        setupObservers()
    }

    private fun initializeUI() {
        binding.CreateAccountBtn.setOnClickListener {
            handleCreateAccount()
        }

        binding.BtnSignIn.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun setupObservers() {
        registerViewModel.registerResult.observe(this) { isSuccess ->
            if (isSuccess) {
                showToast("Registrasi berhasil")
                navigateToLogin()
            }
        }

        registerViewModel.errorMessage.observe(this) { message ->
            showToast(message)
        }

        registerViewModel.isLoading.observe(this) { isLoading ->
            //showLoading(isLoading)
        }
    }

    private fun handleCreateAccount() {
        val name = binding.SignUpName.text.toString()
        val username = binding.SignUpUsername.text.toString()
        val email = binding.SignUpEmail.text.toString()
        val password = binding.SignUpPassword.text.toString()
        val confirmPassword = binding.SignUpRepeatPassword.text.toString()
        val phoneNumber = binding.SignUpPhoneNumber.text.toString()

        if (validateInput(name, username, email, password, confirmPassword, phoneNumber)) {
            registerViewModel.registerUser(User())
        }
    }

    private fun validateInput(
        name: String,
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        phoneNumber: String
    ): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.SignUpNameLayout.error = "Nama tidak boleh kosong"
            isValid = false
        } else {
            binding.SignUpNameLayout.error = null
        }

        if (username.isEmpty()) {
            binding.SignUpUsernameLayout.error = "Username tidak boleh kosong"
            isValid = false
        } else {
            binding.SignUpUsernameLayout.error = null
        }

        if (email.isEmpty()) {
            binding.SignUpEmailLayout.error = "Email tidak boleh kosong"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.SignUpEmailLayout.error = "Format email tidak valid"
            isValid = false
        } else {
            binding.SignUpEmailLayout.error = null
        }

        if (phoneNumber.isEmpty()) {
            binding.SignUpPhoneNumberLayout.error = "Nomor telepon tidak boleh kosong"
            isValid = false
        } else if (!phoneNumber.matches(Regex("^[0-9]{10,13}$"))) {
            binding.SignUpPhoneNumberLayout.error = "Nomor telepon tidak valid"
            isValid = false
        } else {
            binding.SignUpPhoneNumberLayout.error = null
        }

        if (password.isEmpty()) {
            binding.SignUpPasswordLayout.error = "Password tidak boleh kosong"
            isValid = false
        } else if (password.length < 6) {
            binding.SignUpPasswordLayout.error = "Password harus minimal 6 karakter"
            isValid = false
        } else {
            binding.SignUpPasswordLayout.error = null
        }

        if (confirmPassword.isEmpty()) {
            binding.SignUpRepeatPasswordLayout.error = "Konfirmasi password tidak boleh kosong"
            isValid = false
        } else if (confirmPassword != password) {
            binding.SignUpRepeatPasswordLayout.error = "Konfirmasi password tidak cocok"
            isValid = false
        } else {
            binding.SignUpRepeatPasswordLayout.error = null
        }

        return isValid
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
