package com.capstone.skinsense.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.skinsense.data.api.ApiConfig
import com.capstone.skinsense.data.response.User
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(user: User) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Memanggil API menggunakan ApiConfig
                val response = ApiConfig.getApiService().signup(user)

                // Mengatur hasil register berdasarkan respons dari API
                if (response.equals(user)) {
                    _registerResult.value = true
                } else {
                    _registerResult.value = false
                    _errorMessage.value = response.message
                }
            } catch (e: Exception) {
                // Menangani kesalahan saat memanggil API
                _registerResult.value = false
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                // Menyembunyikan indikator loading
                _isLoading.value = false
            }
        }
    }
}