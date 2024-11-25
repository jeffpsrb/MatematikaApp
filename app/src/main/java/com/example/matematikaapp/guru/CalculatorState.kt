package com.example.matematikaapp.guru

//untuk menangani fungsi menambahkan input, melihat input dan menghapus input
data class CalculatorState(
    //variabel untuk menyimpan input data dari user dengan tipe data String dengan nilai awal adalah string kosong
    val input: String = "",

    //variabel untuk menyimpan output dengan tipe data string
    val result: String = "0"
)