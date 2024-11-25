package com.example.matematikaapp.siswa

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.matematikaapp.Screen
import com.example.matematikaapp.ui.theme.PrimaryColor

data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.White,
    val strokeWidth: Dp = 33.dp
)

@Composable
fun CanvasSiswa(
    navController: NavController,
    identitas:String,
    token:String
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val lines = remember { mutableStateListOf<Line>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = {navController.navigate(route = Screen.TokenSiswa.createRoute(identitas))},
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .width(100.dp)
                .align(Alignment.Start)
                .padding(top = 45.dp, start = 20.dp)
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                contentColor = Color.White
            ),
        ) {
            Text(
                text = "Back",
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "Hallo $identitas",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp)

        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Gambar angka",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Jawaban anda : 1",
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Canvas(
            modifier = Modifier
                .width(316.dp)
                .height(314.dp)
                .pointerInput(true) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val line = Line(
                            start = change.position - dragAmount,
                            end = change.position
                        )

                        lines.add(line)
                    }
                }
                .background(color = Color.Black)
                .clipToBounds()
                .align(Alignment.CenterHorizontally)
        ) {
            lines.forEach {
                line -> drawLine(
                    color = line.color,
                    start = line.start,
                    end = line.end,
                    strokeWidth = line.strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    lines.clear()
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(85.dp)
                    .height(47.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                )

            ) {
                Text(text = "Clear")
            }
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(95.dp)
                    .height(47.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                )

            ) {
                Text(text = "Submit")
            }
        }
    }
}



@Preview (showBackground = true)
@Composable
fun CanvasSiswaScreen() {
    CanvasSiswa(
        navController = rememberNavController(),
        identitas = "Tes",
        token = "1234"
    )
}