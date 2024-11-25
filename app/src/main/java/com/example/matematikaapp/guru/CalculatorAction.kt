package com.example.matematikaapp.guru

//membuat sealed class yang sub-class nya akan mewakili setiap operasi yang akan digunakan pada fitur ini
sealed class CalculatorAction {
    //data class untuk merekem perubahan input dari user
    data class InputChange(val input: String): CalculatorAction()

    //object untuk menghapus input
    object ClearInput: CalculatorAction()

    //objek untuk menghitung hasil kalkulasi
    object Evaluate: CalculatorAction()

    //objek untuk menghapus semua input
    object DeleteInput: CalculatorAction()
}