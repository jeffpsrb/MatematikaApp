package com.example.matematikaapp

import okhttp3.Route

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Siswa: Screen(route = "siswa_screen")
    object TokenSiswa: Screen(route = "token_siswa_screen/{identitas}") {
        fun createRoute(identitas:String) = "token_siswa_screen/$identitas"
    }
    object Canvas: Screen(route = "canvas_siswa/{identitas}/{token}") {
        fun createRoute(identitas: String, token: String) = "canvas_siswa/$identitas/$token"
    }

}