package day01

import utils.*

fun main() {
    var currentDialPosition = 50
    var password = 0
    setInputIoStartTime()
    val input = getInput(1)
    setInputParseStartTime()
    val turns = parseInput2(input)
    setAlgorithmStartTime()
    turns.forEach {
        if (it.second > 0) {
            password += it.second
        }
        var newDialPosition = currentDialPosition + it.first
        if (newDialPosition < 0) {
            newDialPosition += 100
            if (currentDialPosition != 0) {
                password++
            }
        } else if (newDialPosition > 99) {
            newDialPosition %= 100
            password++
        } else if (newDialPosition == 0) {
            password++
        }
        currentDialPosition = newDialPosition
    }
    println(getElapsedTime())
    println(password)
}

fun parseInput2(input: String): List<Pair<Int, Int>> = input
    .split("\n")
    .filter { it.isNotBlank() }
    .map {
        val direction = it.first()
        val number = it.slice(1..<it.length).toInt()
        val clicks = number % 100
        val fullTurns = number / 100
        if (direction == 'L') {
            clicks * -1 to fullTurns
        } else {
            clicks to fullTurns
        }
    }