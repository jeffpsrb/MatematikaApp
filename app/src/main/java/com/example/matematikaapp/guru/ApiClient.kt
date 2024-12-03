package com.example.matematikaapp.guru

import com.example.matematikaapp.siswa.ApiService
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

object ApiClient {
    private val BASE_URL = "http://localhost:3000/api/role_guru/"
    val apiServiceGuru:ApiServiceGuru by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiServiceGuru::class.java)
    }

}

interface ApiServiceGuru {
    @POST("guru")
    suspend fun dataGuru (
        @Body guruRequest: GuruRequest
    ): Response<GuruResponse>

    @GET("jawaban")
    suspend fun getJawaban () :Response<JawabanResponse>

    @GET("leaderboard")
    suspend fun getLeaderboard(): Response<LeaderboardResponse>
}

data class GuruRequest (
    val token: String,
    val question:String,
    val true_answer:String
)

data class GuruResponse (
    val message: String
)

data class JawabanRequest (
    @SerializedName("true_answer") val trueAnswer: String
)
data class JawabanResponse (
    @SerializedName("response") val response: List<JawabanRequest>
)

data class LeaderboardData (
    @SerializedName("name") val name:String,
    @SerializedName("is_true") val isTrue: String
)
data class LeaderboardResponse (
    @SerializedName("response") val responseLeaderboard: List<LeaderboardData>
)