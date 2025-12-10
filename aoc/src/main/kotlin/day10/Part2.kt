package day10

import utils.*

val testInput = """
[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
""".trimIndent()

fun main() {
    setInputIoStartTime()
    val input = testInput
    // val input = getInput()
    setInputParseStartTime()
    val machines = parseInput1(input)
    setAlgorithmStartTime()
    var total = 0
    for (machine in machines) {
        var best = Int.MAX_VALUE
        var joltages = machine.joltages.map { 0 }
        val result = check(joltages, machine.joltages, best, 0, machine.buttons)
        total += result
    }
    println(getElapsedTime())
    println(total)
}

fun check(joltages: List<Int>, targetJoltages: List<Int>, best: Int, step: Int, buttons: List<List<Int>>): Int {
    val currentStep = step + 1
    var nextBest = best
    for (button in buttons) {
        val newJoltages = pressButton(joltages, button)
        if (isAtTarget(newJoltages, targetJoltages)) {
            if (currentStep <= best) {
                return currentStep
            }
        } else if (!isOverTarget(newJoltages, targetJoltages)) {
            val nextCheck = check(newJoltages, targetJoltages, nextBest, currentStep, buttons)
            if (nextCheck < nextBest) {
                nextBest = nextCheck
            }
        }
    }
    return nextBest
}

fun isOverTarget(joltages: List<Int>, targetJoltages: List<Int>): Boolean {
    for (i in joltages.indices) {
        if (joltages[i] > targetJoltages[i]) {
            return true
        }
    }
    return false
}

fun isAtTarget(joltages: List<Int>, targetJoltages: List<Int>): Boolean {
    for (i in joltages.indices) {
        if (joltages[i] != targetJoltages[i]) {
            return false
        }
    }
    return true
}

fun pressButton(joltages: List<Int>, button: List<Int>): List<Int> {
    val newJoltages = joltages.toMutableList()
    for (i in button) {
        newJoltages[i]++
    }
    return newJoltages
}