package com.example.matematikaapp.siswa

import android.content.Context
import android.graphics.Bitmap
import com.example.matematikaapp.ml.ModelBest
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class DigitClasifier(private val context: Context)  {
    private val inputSize = 28

    private fun convertBitmapToByteArray (bitmap: Bitmap) : ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(inputSize * inputSize * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(inputSize * inputSize)
        bitmap.getPixels(pixels, 0, inputSize, 0, 0, inputSize, inputSize)
        for (pixel in pixels) {
            val normalized = (pixel and 0xFF).toFloat() / 255.0f
            byteBuffer.putFloat(normalized)
        }

        return byteBuffer
    }

    fun performInference(bitmap: Bitmap): Int {
        val model = ModelBest.newInstance(context)
        val byteBuffer = convertBitmapToByteArray(bitmap)
        val inputFeature = TensorBuffer.createFixedSize(intArrayOf(1, inputSize, inputSize, 1), org.tensorflow.lite.DataType.FLOAT32)
        inputFeature.loadBuffer(byteBuffer)

        val output = model.process((inputFeature))
        val outputFeature = output.outputFeature0AsTensorBuffer

        model.close()

        val outputArray = outputFeature.floatArray
        return outputArray.indices.maxByOrNull { outputArray[it] }?: -1
    }

}