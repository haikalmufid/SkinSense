package com.capstone.skinsense.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinsense.data.api.ApiConfig
import com.capstone.skinsense.data.response.User
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Fungsi untuk menangani proses login.
     */
    fun handleLogin(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Username dan Password tidak boleh kosong"
            return
        }

        val user = User(username = username, password = password)
        performLogin(user)
    }

    /**
     * Memanggil API untuk melakukan login.
     */
    private fun performLogin(user: User) {
        _isLoading.value = true
        viewModelScope.launch {
            try {

                val response = ApiConfig.getApiService().login(user)

                if (response.status == 200) {
                    _loginResult.value = true
                } else {
                    _errorMessage.value = response.message ?: "Login gagal"
                }
            } catch (e: Exception) {
                // Menangani error seperti masalah jaringan atau server
                _errorMessage.value = e.message ?: "Terjadi kesalahan pada koneksi"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
