package day04

import utils.*

const val toilePaperRoll = '@'
const val adjacentLimit = 4

fun main() {
    setInputIoStartTime()
    val input = getInput(4)
    setInputParseStartTime()
    val map = parseInput1(input)
    setAlgorithmStartTime()
    var count = 0
    map.forEachIndexed { y, row ->
        row.forEachIndexed { x, it ->
            if (it == toilePaperRoll && isAccessible(map, x, y)) {
                count++
            }
        }
    }
    println(getElapsedTime())
    println(count)
}

fun isAccessible(map: List<List<Char>>, x: Int, y: Int): Boolean {
    if (y < 0 || x < 0 || y >= map.size || x >= map[y].size) {
        return false
    }
    val height = map.size
    val width = map[y].size
    var count = 0
    for (y2 in y - 1..y + 1) {
        for (x2 in x - 1..x + 1) {
            if (y == y2 && x == x2 || (y2 < 0 || x2 < 0 || y2 >= height || x2 >= width)) {
                continue
            }
            if (map[y2][x2] == toilePaperRoll) {
                count++
            }
        }
    }
    return count < adjacentLimit
}

fun parseInput1(input: String): List<List<Char>> = input
    .lines()
    .filter { it.isNotBlank() }
    .map { it.toCharArray().toList() }