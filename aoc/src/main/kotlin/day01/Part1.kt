package day01

import utils.*

fun main() {
    var currentDialPosition = 50
    var password = 0
    setInputIoStartTime()
    val input = getInput(1)
    setInputParseStartTime()
    val turns = parseInput(input)
    setAlgorithmStartTime()
    turns.forEach {
        var newDialPosition = currentDialPosition + it
        if (newDialPosition < 0) {
            newDialPosition += 100
        } else if (newDialPosition > 99) {
            newDialPosition %= 100
        }
        if (newDialPosition == 0) {
            password++
        }
        currentDialPosition = newDialPosition
    }
    println(getElapsedTime())
    println(password)
}

fun parseInput(input: String): List<Int> = input
    .split("\n")
    .filter { it.isNotBlank() }
    .map {
        val direction = it.first()
        val number = it.slice(1..<it.length).toInt() % 100
        if (direction == 'L') {
            number * -1
        } else {
            number
        }
    }