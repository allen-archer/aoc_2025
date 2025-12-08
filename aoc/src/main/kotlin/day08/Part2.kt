package day08

import utils.*

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
    var answer = 0
    for (distance in distances) {
        val leftCircuit = circuits.first { it.contains(distance.first.first) }
        val rightCircuit = circuits.first { it.contains(distance.first.second) }
        if (leftCircuit != rightCircuit) {
            leftCircuit.addAll(rightCircuit)
            circuits.remove(rightCircuit)
            if (circuits.size == 1) {
                answer = distance.first.first.first * distance.first.second.first
                break
            }
        }
    }
    println(getElapsedTime())
    println(answer)
}

