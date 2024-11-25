package com.example.matematikaapp.siswa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matematikaapp.Screen
import com.example.matematikaapp.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TokenScreenSiswa(
    navController: NavController,
    identitas: String
) {
    var tokenSiswa by remember { mutableStateOf("") }
    var errorToken by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Token Soal $identitas",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 50.dp)
        )
        TextField(
            value = tokenSiswa,
            onValueChange = {tokenSiswa = it},
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = PrimaryColor,
                focusedLabelColor = PrimaryColor,
                containerColor = Color.Transparent,
                textColor = Color.Black
            ),
            modifier = Modifier.padding(start = 50.dp)
        )
        if(tokenSiswa.isEmpty()) {
            Text(
                text = errorToken,
                fontSize = 12.sp,
                color = Color.Red,
                modifier = Modifier
                    .padding(start = 50.dp, top = 2.dp)
                    .align(Alignment.Start)
            )
        }
        Button(
            onClick = {
                if(tokenSiswa.isEmpty()){
                    errorToken = "Identitas siswa belum diisi"
                }
                else {
                    navController.navigate(route = Screen.Canvas.createRoute(identitas, tokenSiswa))
                }
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .width(247.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                contentColor = Color.White
            ),
        ) {
            Text(
                text = "Next",
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Preview (showBackground = true)
@Composable
fun TokenScreenSiswaPrev() {
    TokenScreenSiswa(
        navController = rememberNavController(),
        identitas = "Jeffrey"
    )
}