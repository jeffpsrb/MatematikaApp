package com.example.matematikaapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.matematikaapp.guru.CalculatorViewModel
import com.example.matematikaapp.guru.LeaderboardScreen
import com.example.matematikaapp.guru.LeaderboardViewModel
import com.example.matematikaapp.guru.SoalScreen
import com.example.matematikaapp.guru.TokenInputScreen
import com.example.matematikaapp.siswa.CanvasSiswa
import com.example.matematikaapp.siswa.DigitClasifier
import com.example.matematikaapp.siswa.JawabanViewModel
import com.example.matematikaapp.siswa.SiswaScreen
import com.example.matematikaapp.siswa.TokenScreenSiswa
import com.example.matematikaapp.siswa.TokenViewModel

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
            val tokenViewModel = viewModel<TokenViewModel>()
            TokenScreenSiswa(navController = navController, identitas = identitas, tokenViewModel)
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
            val digitClasifier = DigitClasifier(navController.context)
            val jawabanViewModel = viewModel<JawabanViewModel>()
            CanvasSiswa(navController = navController, identitas = identitas, token = token, digitClasifier = digitClasifier, jawabanViewModel)
        }
        composable(
            route = Screen.TokenScreen.route
        ) {
            TokenInputScreen(navController = navController)
        }
        composable(
            route = Screen.SoalScreen.route,
            arguments = listOf(
                navArgument("token_soal") {
                    type = NavType.StringType
                }
            )
        ) {
            val token_soal = it.arguments?.getString("token_soal") ?: ""
            val calculatorViewModel = viewModel<CalculatorViewModel>()
            SoalScreen(modifier = Modifier, calculatorViewModel = calculatorViewModel, navController = navController, token = token_soal)
        }
        composable(
            route = Screen.LeaderboardScreen.route
        ) {
            val leaderboardViewModel = viewModel<LeaderboardViewModel>()
            LeaderboardScreen(viewModel = leaderboardViewModel, navController = navController)
        }

    }
}