package com.example.matematikaapp.siswa

import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class ImageProcessor {
    fun preprocessImage(mat: Mat): Bitmap {
        var grayMat = Mat()
        if (mat.channels() == 3) {
            Imgproc.cvtColor(mat, grayMat, Imgproc.COLOR_BGR2GRAY)
        } else {
            grayMat = mat.clone()
        }

        val resized = Mat()
        Imgproc.resize(grayMat, resized, Size(28.0, 28.0))

        val outputBitmap = Bitmap.createBitmap(28, 28, Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(resized, outputBitmap)

        return outputBitmap
    }

    fun findContours(bitmap: Bitmap): MutableList<MatOfPoint> {
        val mat = Mat()
        Utils.bitmapToMat(bitmap, mat)

        val gray = Mat()
        Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY)

        val blurred = Mat()
        Imgproc.GaussianBlur(gray, blurred, Size(5.0, 5.0), 0.0)

        val threshold = Mat()
        Imgproc.threshold(blurred, threshold, 15.0, 255.0, Imgproc.THRESH_BINARY)

        val contours = mutableListOf<MatOfPoint>()
        val hirarchy = Mat()
        Imgproc.findContours(threshold, contours, hirarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)

        return contours
    }
}