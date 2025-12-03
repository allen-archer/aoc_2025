package day03

import utils.*
import kotlin.math.pow

fun main() {
    setInputIoStartTime()
    val input = getInput(3)
    setInputParseStartTime()
    val banks = parseInput1(input)
    setAlgorithmStartTime()
    var total = 0L
    banks.forEach { bank ->
        var location = 0
        var joltage = 0L
        for (power in 11 downTo 0) {
            val multiplier = 10.toDouble().pow(power).toLong()
            val batteryLocation = findBattery(bank, location, bank.size - power - 1)
            val battery = bank[batteryLocation]
            location = batteryLocation + 1
            joltage += battery.toLong() * multiplier
        }
        total += joltage
    }
    println(getElapsedTime())
    println(total)
}

fun findBattery(bank: List<Int>, start: Int, end: Int): Int {
    var max = 0
    var location = 0
    for (i in start..end) {
        val battery = bank[i]
        if (battery > max) {
            max = battery
            location = i
            if (battery == 9) {
                break
            }
        }
    }
    return location
}