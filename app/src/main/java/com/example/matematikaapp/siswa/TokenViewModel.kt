package com.example.matematikaapp.siswa

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TokenViewModel: ViewModel() {
    private val _tokenSoal = mutableStateOf<String?>(null)
    val tokenSoal: State<String?> = _tokenSoal

    fun loadTokenSoal() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.tokenSiswa()
                if(response.isSuccessful) {
                    val tokenResponse = response.body()?.responseToken?.getOrNull(0)
                    _tokenSoal.value = tokenResponse?.tokenSoal
                    Log.d("TokenViewModel", "Token Berhasil diambil: $_tokenSoal")
                } else {
                    _tokenSoal.value = null
                }

            } catch (e: Exception) {
                _tokenSoal.value = null
                Log.e("TokenViewModel", "Error: ${e.message}")
            }
        }
    }
}