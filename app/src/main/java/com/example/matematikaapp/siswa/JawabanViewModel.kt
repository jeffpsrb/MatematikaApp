package com.example.matematikaapp.siswa

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matematikaapp.guru.ApiClient
import kotlinx.coroutines.launch

class JawabanViewModel : ViewModel() {
    private val _trueAnswer = mutableStateOf<String?>(null)
    val truAnswer : State<String?> = _trueAnswer

    fun loadJawaban() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiServiceGuru.getJawaban()
                if (response.isSuccessful) {
                    val trueAnswerResponse = response.body()?.response?.getOrNull(0)
                    _trueAnswer.value = trueAnswerResponse?.trueAnswer
                    Log.d("JawabanViewModel", "jawaban berhasil diambil: $_trueAnswer")
                } else {
                    _trueAnswer.value = null
                }

            } catch (e: Exception) {
                _trueAnswer.value = null
                Log.e("JawabanViewModel", "Error: ${e.message}")
            }
        }
    }

}