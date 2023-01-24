package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler = findViewById(R.id.rv_calculator)
        val numberButtonList: ArrayList<NumberLabel> = ArrayList<NumberLabel>()
        numberButtonList.add(NumberLabel("1"))
        numberButtonList.add(NumberLabel("2"))
        numberButtonList.add(NumberLabel("3"))
        numberButtonList.add(NumberLabel("4"))
        numberButtonList.add(NumberLabel("5"))
        numberButtonList.add(NumberLabel("6"))
        numberButtonList.add(NumberLabel("7"))
        numberButtonList.add(NumberLabel("8"))
        numberButtonList.add(NumberLabel("9"))
        numberButtonList.add(NumberLabel("0"))
        numberButtonList.add(NumberLabel("+"))
        numberButtonList.add(NumberLabel("-"))
        numberButtonList.add(NumberLabel("*"))
        numberButtonList.add(NumberLabel("/"))
        numberButtonList.add(NumberLabel("%"))
        numberButtonList.add(NumberLabel("="))
        numberButtonList.add(NumberLabel("R"))
        val position = numberButtonList.size
        println("$position")
        val result = findViewById<TextView>(R.id.tv_output)
        val adapter =
            CalculatorAdapter(numberButtonList, this, findViewById<TextView>(R.id.tv_output))
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(this, 4)
        var temp: String = ""
        var numbers: ArrayList<String> = ArrayList();
        var symbols: ArrayList<String> = ArrayList();
        var output: Double = 0.0
        var first: Double = 0.0
        var second: Double = 0.0
        var isEmptyNumber = true
        var isEmptySymbol = true
        adapter.clickListener = {
            if (it.numAndSym == "R") {
                Log.i("R", "$temp${numbers.toString()}")
                if (temp != "") {
                    temp = temp.dropLast(1)
                    result.text = temp
                    if (numbers.isNotEmpty()) {
                        numbers.remove(numbers.last())
                    } else if (temp == "") {
                        result.text = ""
                        isEmptyNumber = true
                        isEmptySymbol = true
                    }
                } else {
                    result.text = "0.0"
                }
            } else if ("=" != it.numAndSym && it.numAndSym.isDigitsOnly()) {
                temp += it.numAndSym
                result.text = temp
                isEmptyNumber = false
//                numbers.add(it.numAndSym)
            } else if ("=" == it.numAndSym && temp != "") {
                if (temp != "") {
                    numbers = temp.split(Regex("[+|/|\\-|*|%]")) as ArrayList<String>
                } else {
                    result.text = "0.0"
                }
                if (numbers.isNotEmpty() && isEmptySymbol) {
                    result.text = temp
                } else {
                    if (temp.last().isDigit()) {
                        if ((!isEmptySymbol && !isEmptyNumber) && 2 <= numbers.size) {
                            var i = 0
                            while (i != numbers.size - 1) {
                                first = 0.0
                                second = 0.0
                                first = numbers[i].toDouble()
                                second = numbers[i + 1].toDouble()
                                Log.i("output1", "$first$second")
                                when (symbols[i]) {
                                    "+" -> output = first + second
                                    "-" -> output = first - second
                                    "*" -> output = first * second
                                    "/" -> output = first / second
                                    "%" -> output = first % second
                                }
                                numbers[i + 1] = output.toString()
                                Log.i("output1", "$output")
                                i++
                            }
                            result.text = output.toString()
                            temp = ""
                            numbers.clear()
                            symbols.clear()
                        } else if (numbers.size == 1) {
                            result.text = "Enter atleast two numbers"
                        }
                    }
                }
            } else {
                if (!isEmptyNumber && "=" != it.numAndSym) {
                    if(!temp.last().isDigit()) {
                        Log.i("validation","${!temp.last().isDigit()}")
                        temp = temp.dropLast(1)
                        symbols.removeLast()
                        Log.i("validation","${temp}")
                        temp+=it.numAndSym
                        Log.i("validation","${temp}")
                        result.text = temp
                        symbols.add(it.numAndSym)
                    } else{
                        isEmptySymbol = false
                        temp += it.numAndSym
                        result.text = temp
                        Log.i("it.numAndSym", "$it.numAndSym")
                        symbols.add(it.numAndSym)
                    }
                }
            }
        }
    }
}


fun operation(x: Char): Int {
    Log.i("operation", x.toString())
    var first: Int = 0
    var second: Int = 0
    var result: Int = 0
    if (x.isDigit()) {
        second = x.code
    }
    if ('R' != x) {
        when (x) {
            '+' -> first += second
            '-' -> first -= second
        }
    }
    return first
}