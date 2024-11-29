package com.example.matematikaapp.siswa

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matematikaapp.Screen
import com.example.matematikaapp.ui.theme.PrimaryColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiswaScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var identitasSiswa by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }
    var isDataSent by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Identitas Siswa",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 50.dp)
        )
        TextField(
            value = identitasSiswa,
            onValueChange = {identitasSiswa = it},
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = PrimaryColor,
                focusedLabelColor = PrimaryColor,
                containerColor = Color.Transparent,
                textColor = Color.Black
            ),
            modifier = Modifier.padding(start = 50.dp)
        )
        if (identitasSiswa.isEmpty()) {
            Text(
                text = errorText,
                fontSize = 12.sp,
                color = Color.Red,
                modifier = Modifier
                    .padding(start = 50.dp, top = 2.dp)
                    .align(Alignment.Start)
            )
        }
        Button(
            onClick = {
                if(identitasSiswa.isEmpty()){
                    errorText = "Identitas siswa belum diisi"
                }
                else {
                    scope.launch {
                        submitData(identitasSiswa, context)
                    }
                    isDataSent = true
                    if(isDataSent) {
                        navController.navigate(route = Screen.TokenSiswa.createRoute(identitasSiswa))
                    } else {
                        Toast.makeText(context, "Data Not Submited", Toast.LENGTH_SHORT).show()
                    }
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

suspend fun submitData(name:String, context: Context) {
    try {
        val response = ApiClient.apiService.dataSiswa(SiswaRequest(name))
        if(response.isSuccessful) {
            val message = response.body()?.message?: "Data Submited"
            Toast.makeText(context, message, Toast.LENGTH_SHORT). show()
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}


@Preview (showBackground = true)
@Composable
fun SiswaScreenPrev() {
    SiswaScreen(navController = rememberNavController())
}