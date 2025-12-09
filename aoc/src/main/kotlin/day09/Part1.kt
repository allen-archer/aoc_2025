package day09

import utils.*
import kotlin.math.abs

fun main() {
    setInputIoStartTime()
    val input = getInput(9)
    setInputParseStartTime()
    val tiles = parseInput1(input)
    setAlgorithmStartTime()
    var max = 0L
    for (i in tiles.indices) {
        val left = tiles[i]
        for (j in i + 1..<tiles.size) {
            val right = tiles[j]
            val width = 1 + abs(left.first - right.first)
            val height = 1 + abs(left.second - right.second)
            val area = width * height
            if (area > max) {
                max = area
            }
        }
    }
    println(getElapsedTime())
    println(max)
}

fun parseInput1(input: String): List<Pair<Long, Long>> = input.lines()
    .filter { it.isNotBlank() }
    .map {
        val split = it.split(",")
        split[0].toLong() to split[1].toLong()
    }