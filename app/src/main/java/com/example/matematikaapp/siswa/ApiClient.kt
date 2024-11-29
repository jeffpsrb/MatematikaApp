package com.example.matematikaapp.siswa

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

object ApiClient {
    private const val BASE_URL = "http://localhost:3000/api/role_siswa/"
    val apiService:ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @POST("siswa")
    suspend fun dataSiswa (
        @Body siswaRequest: SiswaRequest
    ): Response<SiswaResponse>

    @Multipart
    @POST("siswa_answer")
    suspend fun dataJawaban (
        @Part image: MultipartBody.Part,
        @Part("student_answer") studentAnswer: RequestBody
    ): Response<ResponseBody>

    @GET("token_siswa")
    suspend fun tokenSiswa(): Response<TokenResponse>
}


data class SiswaRequest (
    val name: String
)
data class SiswaResponse (
    val message: String
)
data class TokenData (
    @SerializedName("token") val tokenSoal:String
)
data class TokenResponse (
    @SerializedName("response") val responseToken: List<TokenData>
)