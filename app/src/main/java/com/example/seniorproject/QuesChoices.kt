package com.example.seniorproject

class QuesChoices (var type: String, var question: String, var choices : MutableList<String>, var points: MutableList<Int>){
    constructor(): this("","", mutableListOf(), mutableListOf())
}
