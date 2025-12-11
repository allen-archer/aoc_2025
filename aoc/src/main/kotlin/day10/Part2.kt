package day10

import utils.*

val testInput = """
[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
""".trimIndent()

var cache: MutableMap<Pair<List<Int>, Int>, Int> = mutableMapOf()

fun main() {
    setInputIoStartTime()
//    val input = testInput
     val input = getInput(10)
    setInputParseStartTime()
    val machines = parseInput1(input)
    setAlgorithmStartTime()
    var total = 0
    for (machine in machines) {
        cache = mutableMapOf()
        val result = check(machine.joltages.map { 0 }, machine.joltages, machine.buttons)
        println(result)
        total += result
    }
    println(getElapsedTime())
    println(total)
}

fun check(joltages: List<Int>, targetJoltages: List<Int>, buttons: List<List<Int>>): Int {
    var nextBest = Int.MAX_VALUE
    for (i in buttons.indices) {
        if (cache.contains(joltages to i)) {
            val cachedVal = cache[joltages to i]!!
            if (cachedVal != Int.MAX_VALUE) {
                val nextVal = cachedVal + 1
                if (nextVal < nextBest) {
                    nextBest = nextVal
                }
            }
        } else {
            val button = buttons[i]
            val newJoltages = pressButton(joltages, button)
            if (isAtTarget(newJoltages, targetJoltages)) {
                cache[joltages to i] = 0
                nextBest = 1
            } else if (!isOverTarget(newJoltages, targetJoltages)) {
                var nextCheck = check(newJoltages, targetJoltages, buttons)
                cache[joltages to i] = nextCheck
                if (nextCheck != Int.MAX_VALUE) {
                    nextCheck++
                }
                if (nextCheck < nextBest) {
                    nextBest = nextCheck
                }
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