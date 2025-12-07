package day07

import utils.*

val cache: MutableMap<Pair<Int, Int>, Long> = mutableMapOf()
var map: List<List<Char>> = listOf()

fun main() {
    setInputIoStartTime()
    val input = getInput(7)
    setInputParseStartTime()
    map = parseInput1(input)
    height = map.indices
    width = map[0].indices
    setAlgorithmStartTime()
    val startLocation = map[0].indexOf(start) to 0
    val splits = moveBeam(startLocation) + 1
    println(getElapsedTime())
    println(splits)
}

fun moveBeam(beamLocation: Pair<Int, Int>): Long {
    if (cache.containsKey(beamLocation)) {
        return cache[beamLocation]!!
    }
    val belowLocation = beamLocation.first to beamLocation.second + 1
    if (belowLocation.second !in height) {
        return 0
    }
    if (cache.containsKey(belowLocation)) {
        return cache[belowLocation]!!
    }
    val below = map[belowLocation.second][belowLocation.first]
    var splits = 0L
    if (below == empty) {
        splits = moveBeam(belowLocation)
    } else if (below == splitter) {
        splits++
        val leftLocation = belowLocation.first - 1 to belowLocation.second
        val left = if (leftLocation.first in width) map[leftLocation.second][leftLocation.first] else '!'
        if (left == empty || left == splitter) {
            splits += moveBeam(leftLocation)
        }
        val rightLocation = belowLocation.first + 1 to belowLocation.second
        val right = if (rightLocation.first in width) map[rightLocation.second][rightLocation.first] else '!'
        if (right == empty || right == splitter) {
            splits += moveBeam(rightLocation)
        }
    }
    cache[beamLocation] = splits
    return splits
}