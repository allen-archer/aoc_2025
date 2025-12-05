package day04

import utils.*

var height = 0
var width = 0

fun main() {
    setInputIoStartTime()
    val input = getInput(4)
    setInputParseStartTime()
    val map = parseInput1(input)
    setAlgorithmStartTime()
    height = map.size
    width = map[0].size
    val adjacencyMap = createAdjacencyMap(map)
    var anyRemoved = true
    var count = 0
    while (anyRemoved) {
        anyRemoved = false
        for (y in 0..<height) {
            for (x in 0..<width) {
                if (adjacencyMap[y][x] in 0..<adjacentLimit) {
                    count++
                    anyRemoved = true
                    removeToiletPaperRoll(adjacencyMap, x, y)
                }
            }
        }
    }
    println(getElapsedTime())
    println(count)
}

fun createAdjacencyMap(map: List<List<Char>>): List<MutableList<Int>> {
    val adjacencyMap = List(height) { MutableList(width) { -1 } }
    for (y in 0..<height) {
        for (x in 0..<width) {
            if (map[y][x] != toilePaperRoll) {
                continue
            }
            adjacencyMap[y][x]++
            for (y2 in y - 1..y + 1) {
                for (x2 in x - 1..x + 1) {
                    if (y == y2 && x == x2 || (y2 < 0 || x2 < 0 || y2 >= height || x2 >= width)) {
                        continue
                    }
                    if (map[y2][x2] == toilePaperRoll) {
                        adjacencyMap[y2][x2]++
                    }
                }
            }
        }
    }
    return adjacencyMap
}

fun removeToiletPaperRoll(adjacencyMap: List<MutableList<Int>>, x: Int, y: Int) {
    adjacencyMap[y][x] = -1
    for (y2 in y - 1..y + 1) {
        for (x2 in x - 1..x + 1) {
            if (y == y2 && x == x2 || (y2 < 0 || x2 < 0 || y2 >= height || x2 >= width)) {
                continue
            }
            if (adjacencyMap[y2][x2] >= adjacentLimit) {
                adjacencyMap[y2][x2]--
            }
        }
    }
}