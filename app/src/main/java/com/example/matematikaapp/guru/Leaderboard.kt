package com.example.matematikaapp.guru

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matematikaapp.Screen
import com.example.matematikaapp.ui.theme.PrimaryColor

@Composable
fun LeaderboardScreen(
    viewModel: LeaderboardViewModel,
    navController: NavController
) {
    var showRefreshedNotification by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.loadLeaderboardData()
    }
    if (showRefreshedNotification) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            dismissAction = { showRefreshedNotification = false },
            content = { Text("Data refreshed!") }
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Leaderboard",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Header Row
        RowHeader()

        // LazyColumn for data rows
        LazyColumn {
            items(viewModel.dataLeaderboard.value) { siswa ->
                RowData(siswa = siswa)
            }
        }

        FloatingActionButton(
            onClick = {
                viewModel.refreshIdentitasSiswa()
                showRefreshedNotification = true
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Refresh",
                tint = PrimaryColor
            )
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = {
                    navController.navigate(Screen.SoalScreen.route)
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(85.dp)
                    .height(47.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                ),
            ) {
                Text(text = "Back")
            }

        }

    }
}

@Composable
fun RowHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor,
            contentColor = Color.White
        )
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Siswa",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Benar/Salah",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }
}

@Composable
fun RowData(siswa: LeaderboardData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor,
            contentColor = Color.White
        )
    ) {
            
        }
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = siswa.name,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (siswa.isTrue == "1") "Benar" else "Salah",
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }


@Preview(showBackground = true)
@Composable
fun LeaderboardScreenPreview() {
    val viewModel = remember { LeaderboardViewModel() }
    LeaderboardScreen(viewModel = viewModel, rememberNavController())
}
