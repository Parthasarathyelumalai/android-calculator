package com.example.calculator

fun main() {
    val stringToSplit = "11+2/3-4*5+1-1/22%1"
    var split = stringToSplit.split(Regex ("[0-9]*")) as ArrayList<String>
    println(split.toString())
}