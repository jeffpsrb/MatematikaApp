package com.example.matematikaapp.siswa

import android.content.Context
import android.graphics.Bitmap
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
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import java.io.ByteArrayOutputStream
import java.io.File.separator

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
    token:String,
    digitClasifier: DigitClasifier,
    viewModel: JawabanViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val lines = remember { mutableStateListOf<Line>() }
    var isDataSent by remember { mutableStateOf(false) }
    var jawaban by remember { mutableStateOf("") }
    var isTrue by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadJawaban()
    }
    val trueAnswer = viewModel.truAnswer.value
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
            text = "Jawaban anda : $jawaban",
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
                onClick = {
                    val bitmap = convertToBitmap(lines)
                    val mat = bitmapToMat(bitmap)
                    val preprocessed =ImageProcessor()
                    val contours =preprocessed.findContours(bitmap)
                    val sortedContours =contours.sortedBy { contour ->
                        Imgproc.boundingRect(contour).x
                    }
                    val result = sortedContours.map { contour ->
                        val react = Imgproc.boundingRect(contour)
                        val digitMat =Mat(mat, react)
                        val preprocessedDigit =preprocessed.preprocessImage(digitMat)
                        digitClasifier.performInference(preprocessedDigit)
                    }
                    jawaban = result.joinToString(separator = "") {it.toString()}
                    Toast.makeText(context, "Predicted digits: $jawaban", Toast.LENGTH_SHORT).show()

                    if (jawaban == trueAnswer) {
                        isTrue = "1"
                    } else {
                        isTrue = "0"
                    }

                    if (!isDataSent) {
                        val byteArray = convertCanvasToJpg(lines)
                        if (byteArray != null) {
                            val imageMedia = "image/jpeg".toMediaTypeOrNull()
                            val imageRequestBody =byteArray.toRequestBody(imageMedia)
                            val studentAnswerRequestBody = jawaban.toRequestBody("text/plain".toMediaTypeOrNull())
                            val isTrueRequestBody = isTrue.toRequestBody("text/plain".toMediaTypeOrNull())
                            val imagePart = MultipartBody.Part.createFormData("image", "gambar_${identitas}_${token}_${jawaban}", imageRequestBody)
                            scope.launch {
                                submit(imagePart, studentAnswerRequestBody, isTrueRequestBody, context)
                            }
                            isDataSent = true
                        } else {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Data has been sent already", Toast.LENGTH_SHORT).show()
                    }
                },
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


fun convertCanvasToJpg(lines: List<Line>): ByteArray {
    val bitmap = Bitmap.createBitmap(948, 942, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    lines.forEach {line ->
        canvas.drawLine(
            line.start.x,
            line.start.y,
            line.end.x,
            line.end.y,
            Paint().apply {
                color = line.color.toArgb()
                strokeWidth = line.strokeWidth.value
                strokeCap =Paint.Cap.ROUND
            }
        )

    }
    val output =ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
    return output.toByteArray()
}

fun convertToBitmap (lines: List<Line>): Bitmap {
    val bitmap = Bitmap.createBitmap(948, 942, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    lines.forEach {line ->
        canvas.drawLine(
            line.start.x,
            line.start.y,
            line.end.x,
            line.end.y,
            Paint().apply {
                color = line.color.toArgb()
                strokeWidth = line.strokeWidth.value
                strokeCap =Paint.Cap.ROUND
            }
        )

    }
    return bitmap
}

fun bitmapToMat(bitmap: Bitmap): Mat {
    val mat = Mat()
    Utils.bitmapToMat(bitmap, mat)
    Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY)
    return mat
}

suspend fun submit(image: MultipartBody.Part, studentAnswer:RequestBody, isTrue:RequestBody, context: Context) {
    try {
        val response =ApiClient.apiService.dataJawaban(image, studentAnswer, isTrue)
        if (response.isSuccessful) {
            Toast.makeText(context, "Data berhasil dikirim", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Gagal mengirim data", Toast.LENGTH_SHORT).show()
        }

    } catch (e: Exception){
        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}


//@Preview (showBackground = true)
//@Composable
//fun CanvasSiswaScreen() {
//    CanvasSiswa(
//        navController = rememberNavController(),
//        identitas = "Tes",
//        token = "1234",
//        jawaban = "2"
//    )
//}