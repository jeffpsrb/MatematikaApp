package com.example.matematikaapp.guru

import com.example.matematikaapp.siswa.ApiService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object ApiClientGuru {
    private const val BASE_URL = "http://localhost:3000/api/role_guru"
    val apiService:ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @POST("guru")
suspend fun dataGuru (
        @Body guruRequest: GuruRequest
    ):Response<GuruResponse>
}

data class GuruRequest (
    val token: String,
    val question:String,
    val true_answer:String
)

data class GuruResponse (
    val message: String
)

