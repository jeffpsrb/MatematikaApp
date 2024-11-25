package com.example.matematikaapp.guru

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
fun TokenInputScreen(
    navController: NavController
) {
    var tokenInput by remember { mutableStateOf("") }
    var tokenError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Token Soal",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 50.dp)

        )
        TextField(
            value = tokenInput,
            onValueChange = {tokenInput = it},
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryColor,
                unfocusedLabelColor = PrimaryColor,
                containerColor = Color.Transparent,
                textColor = Color.Black
            ),
            modifier = Modifier.padding(start = 50.dp)
        )
        if(tokenInput.isEmpty()) {
            Text(
                text = tokenError,
                fontSize = 12.sp,
                color = Color.Red,
                modifier = Modifier
                    .padding(start = 50.dp, top = 2.dp)
                    .align(Alignment.Start)
            )
        }
        Button(
            onClick = {
                if(tokenInput.isEmpty()){
                    tokenError = "Token belum diisi"
                }
                else {
                    navController.navigate(route = Screen.SoalScreen.createRoute(tokenInput))
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

@Preview(showBackground = true)
@Composable
fun TokenInputPrev() {
    TokenInputScreen(navController = rememberNavController())
}