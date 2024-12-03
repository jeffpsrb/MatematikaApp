package com.example.matematikaapp.guru

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LeaderboardViewModel : ViewModel() {
    private val _dataLeaderboard = mutableStateOf<List<LeaderboardData>>(emptyList())

    val dataLeaderboard : State<List<LeaderboardData>> = _dataLeaderboard

    fun loadLeaderboardData() {
        viewModelScope.launch {
            val response = ApiClient.apiServiceGuru.getLeaderboard()
            if (response.isSuccessful) {
                _dataLeaderboard.value = response.body()?.responseLeaderboard?: emptyList()
            } else {
                emptyList<List<LeaderboardData>>()
            }
        }
    }

    fun refreshIdentitasSiswa() {
        loadLeaderboardData()
    }
}