package day08

import utils.*
import kotlin.math.pow
import kotlin.math.sqrt

const val connectionsLimit = 1000
const val circuitsLimit = 3

fun main() {
    setInputIoStartTime()
    val input = getInput(8)
    setInputParseStartTime()
    val circuits: MutableList<MutableSet<Triple<Int, Int, Int>>> = parseInput1(input)
    setAlgorithmStartTime()
    val distances: MutableList<Pair<Pair<Triple<Int, Int, Int>, Triple<Int, Int, Int>>, Double>> = mutableListOf()
    val tempList = circuits.toList()
    for (i in tempList.indices) {
        val left = tempList[i].first()
        for (j in i + 1..<tempList.size) {
            val right = tempList[j].first()
            val distance = calculateDistance(left, right)
            val coordinates = left to right
            distances.add(coordinates to distance)
        }
    }
    distances.sortBy { it.second }
    for (i in 0..<connectionsLimit) {
        val distance = distances[i]
        val leftCircuit = circuits.first { it.contains(distance.first.first) }
        val rightCircuit = circuits.first { it.contains(distance.first.second) }
        if (leftCircuit != rightCircuit) {
            leftCircuit.addAll(rightCircuit)
            circuits.remove(rightCircuit)
        }
    }
    val sizes = circuits.map { it.size.toLong() }.sortedDescending().slice(0..<circuitsLimit)
    val total = sizes.reduce { acc, i -> acc * i }
    println(total)
    println(getElapsedTime())
}

fun parseInput1(input: String): MutableList<MutableSet<Triple<Int, Int, Int>>> {
    return input.lines()
        .filter { it.isNotBlank() }
        .map {
            val split = it.split(",")
            mutableSetOf(Triple(split[0].toInt(), split[1].toInt(), split[2].toInt()))
        }
        .toMutableList()
}

fun calculateDistance(left: Triple<Int, Int, Int>, right: Triple<Int, Int, Int>): Double {
    val dx = (left.first - right.first).toDouble()
    val dy = (left.second - right.second).toDouble()
    val dz = (left.third - right.third).toDouble()
    val distanceSquared = dx.pow(2) + dy.pow(2) + dz.pow(2)
    return sqrt(distanceSquared)
}