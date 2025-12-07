package day07

import utils.*

const val start = 'S'
const val empty = '.'
const val splitter = '^'
const val beam = '|'

var width: IntRange = IntRange(0, 0)
var height: IntRange = IntRange(0, 0)

fun main() {
    setInputIoStartTime()
    val input = getInput(7)
    setInputParseStartTime()
    val map: List<MutableList<Char>> = parseInput1(input)
    height = map.indices
    width = map[0].indices
    setAlgorithmStartTime()
    val visitedPoints: MutableSet<Pair<Int, Int>> = mutableSetOf()
    for (row in height) {
        for (column in width) {
            if (map[row][column] == beam || map[row][column] == start) {
                moveBeam(map, visitedPoints, column to row)
            }
        }
    }
    println(getElapsedTime())
    println(visitedPoints.size)
}

fun parseInput1(input: String): List<MutableList<Char>> = input.lines()
    .filter { it.isNotBlank() }
    .map { it.toCharArray().toMutableList() }

fun moveBeam(map: List<MutableList<Char>>, visitedPoints: MutableSet<Pair<Int, Int>>, beamLocation: Pair<Int, Int>) {
    val belowLocation = beamLocation.first to beamLocation.second + 1
    if (belowLocation.second !in height) {
        return
    }
    val below = map[belowLocation.second][belowLocation.first]
    if (below == empty) {
        map[belowLocation.second][belowLocation.first] = beam
    } else if (below == splitter) {
        visitedPoints.add(belowLocation)
        val leftPoint = belowLocation.first - 1 to belowLocation.second
        val left = if (leftPoint.first in width) map[leftPoint.second][leftPoint.first] else '!'
        if (left == empty) {
            map[leftPoint.second][leftPoint.first] = beam
        }
        val rightPoint = belowLocation.first + 1 to belowLocation.second
        val right = if (rightPoint.first in width) map[rightPoint.second][rightPoint.first] else '!'
        if (right == empty) {
            map[rightPoint.second][rightPoint.first] = beam
        }
    }
}