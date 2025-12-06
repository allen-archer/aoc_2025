package day06

import utils.*

fun main() {
    setInputIoStartTime()
    val input = getInput(6)
    setInputParseStartTime()
    val output = parseInput2(input)
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

fun parseInput2(input: String): List<Pair<List<Long>, Char>> {
    val rows = input.lines()
        .filter { it.isNotBlank() }
        .map { it.toCharArray() }
    val output: MutableList<Pair<List<Long>, Char>> = mutableListOf()
    val numberRowCount = rows.size - 1
    var isNewProblem = true
    var currentNumbers: MutableList<Long> = mutableListOf()
    var operator = ' '
    for (column in rows[0].indices) {
        if (!isNewProblem && isColumnEmpty(rows, column)) {
            isNewProblem = true
            output.add(currentNumbers to operator)
            continue
        }
        if (isNewProblem) {
            isNewProblem = false
            currentNumbers = mutableListOf()
            operator = rows.last()[column]
        }
        var number = ""
        for (row in 0..<numberRowCount) {
            if (rows[row][column] != ' ') {
                number += rows[row][column]
            }
        }
        currentNumbers.add(number.toLong())
    }
    if (!isNewProblem) {
        output.add(currentNumbers to operator)
    }
    return output
}

fun isColumnEmpty(rows: List<CharArray>, column: Int): Boolean {
    for (i in rows.indices) {
        if (rows[i][column] != ' ') {
            return false
        }
    }
    return true
}