package day06

import utils.*

fun main() {
    setInputIoStartTime()
    val input = getInput(6)
    setInputParseStartTime()
    val output = parseInput1(input)
    setAlgorithmStartTime()
    var total = 0L
    for ((numbers, operator) in output) {
        when (operator) {
            '+' -> { total += numbers.sum() }
            '*' -> { total += numbers.reduce { acc, number -> acc * number }}
        }
    }
    println(getElapsedTime())
    println(total)
}

fun parseInput1(input: String): List<Pair<List<Long>, Char>> {
    val rows = input.lines()
        .filter { it.isNotBlank() }
        .map { it.split("\\s".toRegex()).filter { s -> s.isNotBlank()} }
    val output: MutableList<Pair<List<Long>, Char>> = mutableListOf()
    for (i in rows[0].indices) {
        val numbers: MutableList<Long> = mutableListOf()
        for (j in 0..< rows.size - 1) {
            numbers.add(rows[j][i].toLong())
        }
        output.add(numbers to rows.last()[i].first())
    }
    return output
}