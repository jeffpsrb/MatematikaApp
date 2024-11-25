package com.example.matematikaapp.guru

//untuk menangani fungsi menambahkan input, melihat input dan menghapus input
object CalculatorStack {
    //function yang akan menambahkan elemen ke dalam input
    //ArrayDeque : Struktur data dinamis untuk menyimpan sekumpulan elemen
    // .push : fungsi untuk menambah elemen ke dalam ArrayDeque
    //addlast : menambahkan elemen ke ujung belakang dari ArrayDeque
    fun<T> ArrayDeque<T>.push(element:T) = addLast(element)

    //function untuk menghapus element
    //removeLastOrNull() : menghapus elemen terakhir dari ArrayDeque dan mengembalikan null jika ArrayDeque kosong
    fun<T> ArrayDeque<T>.pop() = removeLastOrNull()

    //function untuk melihat nilai dari ArrayDeque
    //lastOrNull() : mengembalikan elemen terakhir dari ArrayDeque atau null jika ArrayDeque kosong
    fun<T> ArrayDeque<T>.peek() = lastOrNull()
}