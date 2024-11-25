package com.example.matematikaapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.matematikaapp.siswa.CanvasSiswa
import com.example.matematikaapp.siswa.SiswaScreen
import com.example.matematikaapp.siswa.TokenScreenSiswa

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Siswa.route
        ) {
            SiswaScreen(navController = navController)
        }
        composable(
            route = Screen.TokenSiswa.route,
            arguments = listOf(
                navArgument("identitas") {
                    type = NavType.StringType
                }
            )
        ) {
            val identitas = it.arguments?.getString("identitas") ?: ""
            TokenScreenSiswa(navController = navController, identitas = identitas)
        }
        composable(
            route = Screen.Canvas.route,
            arguments = listOf(
                navArgument("identitas") {
                    type = NavType.StringType
                },
                navArgument("token") {
                    type = NavType.StringType
                }
            )
        ) {
            val identitas = it.arguments?.getString("identitas") ?: ""
            val token = it.arguments?.getString("token") ?: ""
            CanvasSiswa(navController = navController, identitas = identitas, token = token)
        }

    }
}