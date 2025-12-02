package day02

import utils.*

fun main() {
    var total = 0L
    setInputIoStartTime()
    val input = getInput(2)
    setInputParseStartTime()
    val ranges = parseInput1(input)
    setAlgorithmStartTime()
    ranges.forEach {
        val start = it.first.toLong()
        val end = it.second.toLong()
        for (id in start..end) {
            val idString = id.toString()
            if (isIdInvalid(idString)) {
                total += id
            }
        }
    }
    println(getElapsedTime())
    println(total)
}

fun isIdInvalid(id: String): Boolean {
    if (id.length % 2 != 0) {
        return false
    }
    val halfLength = id.length / 2
    val firstHalf = id.substring(0, halfLength)
    val secondHalf = id.substring(halfLength)
    return firstHalf == secondHalf
}

fun parseInput1(input: String): List<Pair<String, String>> {
    return input.split(",")
        .filter { it.isNotBlank() }
        .map {
            val secondSplit = it.split("-")
            secondSplit.first().trim() to secondSplit.last().trim()
        }
}