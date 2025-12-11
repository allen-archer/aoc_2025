package day11

import utils.*

const val end = "out"
var map: Map<String, List<String>>? = null

fun main() {
    setInputIoStartTime()
    val input = getInput(11)
    setInputParseStartTime()
    map = parseInput1(input)
    setAlgorithmStartTime()
    val count = countPaths("you")
    println(getElapsedTime())
    println(count)
}

fun countPaths(vertex: String): Int {
    val otherVertices = map!![vertex]!!
    var total = 0
    for (otherVertex in otherVertices) {
        if (otherVertex == end) {
            total++
        } else {
            total += countPaths(otherVertex)
        }
    }
    return total
}

fun parseInput1(input: String): Map<String, List<String>> = input.lines()
    .filter { it.isNotBlank() }
    .associate {
        val split = it.split(": ")
        split[0] to split[1].split(" ").toList()
    }