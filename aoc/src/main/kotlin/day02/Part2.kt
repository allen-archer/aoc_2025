package day02

import utils.*

val factors = intArrayOf(2, 3, 5, 7)

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
            if (isIdInvalid2(idString)) {
                total += id
            }
        }
    }
    println(getElapsedTime())
    println(total)
}

fun isIdInvalid2(id: String): Boolean {
    for (factor in factors) {
        if (id.length % factor == 0) {
            val gap = id.length / factor
            var areAllMatching = true
            for (i in 0..<gap) {
                val end = (factor - 1) * gap + i
                if (!areCharactersTheSame(id, gap, i, end)) {
                    areAllMatching = false
                }
            }
            if (areAllMatching) {
                return true
            }
        }
    }
    return false
}

fun areCharactersTheSame(string: String, gap: Int, start: Int, end: Int): Boolean {
    val character = string[start]
    for (i in start + gap..end step gap) {
        if (character != string[i]) {
            return false
        }
    }
    return true
}