package com.example.matematikaapp.guru

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.matematikaapp.guru.CalculatorStack.peek
import com.example.matematikaapp.guru.CalculatorStack.pop
import com.example.matematikaapp.guru.CalculatorStack.push
import kotlin.math.pow

//code ini berisi logika yang digunakan untuk fitur membuat soal, dan menghasilkan jawaban
class CalculatorViewModel: ViewModel()  {

    var calculatorState by mutableStateOf(CalculatorState())

    //membuat function yang merekam perubahan input dari user
    //privete function digunakan agar function hanya bisa diakses oleh sub-class dari class CalculatorViewModel
    private fun onInputChange(input: String) {
        //menggabungkan input sebelumnya dengan input baru
        calculatorState.apply { calculatorState = copy(input = this.input + input) }
    }

    //membuat function untuk delete
    private fun onDelate() {
        //menghapus satu karekter terakhir dari input
        calculatorState.apply { calculatorState = copy(input = this.input.dropLast(1)) }
        if(calculatorState.input.isEmpty()) {
            calculatorState.apply { calculatorState = copy(result = "0") }
        }
    }

    //membuat function untuk membersihkan seluruh input
    private fun clearInputChange() {
        calculatorState.apply { calculatorState = copy(input = "") }
        calculatorState.apply { calculatorState = copy(result = "0") }
    }

    //Operasi Aritmatika
    private fun operator(char: Char): Boolean = when(char) {
        '+', '-', '*', '/', '(', ')', '^' -> true
        else -> false
    }

    private fun notOperator(char: Char): Boolean = when(char) {
        '+', '-', '*', '/', '(', ')', '^' -> false
        else -> true
    }

    //function untuk membuat level dari setiap operator, operator mana yang harus dieksekusi terlebih dahulu
    private fun operatorLevel(char: Char?): Int = when(char) {
        '+', '-' -> 1
        '*', '/' -> 2
        '^' -> 3
        else -> -1
    }

    private fun outputConversion(input: String): String {
        var result = ""
        val inputString = ArrayDeque<Char>()

        for(n in input) {
            if(!operator(n)) {
                result += n
            } else if(n == '(') {
                inputString.push(n)
            } else if(n == ')') {
                while(!inputString.isEmpty() && inputString.peek() != '(') {
                    result +=" " + inputString.pop()
                }
                inputString.pop()
            } else {
                while (!inputString.isEmpty() && operatorLevel(n) <= operatorLevel(
                        inputString.peek()
                    )
                ) {
                    result+= " ${inputString.pop()} "
                }
                inputString.push(n)
                result += " "
            }
        }
        result += " "
        while (!inputString.isEmpty()) {
            if (inputString.peek() == '(') return "Invalid"
            result += inputString.pop()!! + " "
        }
        return result.trim()
    }

    private fun replacedNumber(input: String): String {
        val arrayMember = StringBuffer(input)

        if (arrayMember[0] == '-') {
            arrayMember.setCharAt(0, 'n')
        }
        var i = 0
        while (i < arrayMember.length) {
            if (arrayMember[i] == '-') {
                if (arrayMember [i-1] == '+' ||
                    arrayMember [i-1] == '-' ||
                    arrayMember [i-1] == '/' ||
                    arrayMember [i-1] == '*' ||
                    arrayMember [i-1] == '('
                ) {
                    arrayMember.setCharAt(i, 'n')
                }
            }
            i++
        }
        return arrayMember.toString()
    }

    private fun evaluate(input: String): Int? {
        var inputString = ""
        val stack = ArrayDeque<Double>()

        for (i in input) {
            if(notOperator(i) && i != ' ') {
                inputString += i
            } else if (i == ' ' && inputString != "") {
                stack.push(inputString.replace('n', '-').toDouble())
                inputString = ""
            }
            else if (!notOperator(i)) {
                val number1 = stack.pop()
                val number2 = stack.pop()

                when (i) {
                    '+' -> stack.push(number2!! + number1!!)
                    '-' -> stack.push(number2!! - number1!!)
                    '*' -> stack.push(number2!! * number1!!)
                    '/' -> stack.push(number2!! / number1!!)
                    '^' -> stack.push(number2!!.pow(number1!!.toDouble()))
                }
            }
        }
        return stack.pop()!!.toInt()
    }

    private fun result(input: String): String {
        val stringInput = replacedNumber(input)
        val postFix = outputConversion(stringInput)

        if (postFix == "Invalid") {
            return postFix
        }
        return try {
            val evaluation = evaluate(postFix)
            evaluation.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid"
        }

    }

    private fun onResultChange(result: String) {
        calculatorState = calculatorState.copy(result = result)
    }

    private  fun onEvaluateExpression() {
        if (calculatorState.input.isNotBlank()) {
            onResultChange(result = result(calculatorState.input))
        }
    }

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.InputChange -> onInputChange(action.input)
            is CalculatorAction.ClearInput -> clearInputChange()
            is CalculatorAction.Evaluate -> onEvaluateExpression()
            is CalculatorAction.DeleteInput -> onDelate()
        }
    }

}