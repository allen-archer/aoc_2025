package day03

import utils.*

fun main() {
    setInputIoStartTime()
    val input = getInput(3)
    setInputParseStartTime()
    val banks = parseInput1(input)
    setAlgorithmStartTime()
    var total = 0
    banks.forEach { bank ->
        val firstBatteryLocation = findFirstBattery(bank)
        val secondBatteryLocation = findSecondBattery(bank, firstBatteryLocation)
        val firstBattery = bank[firstBatteryLocation]
        val secondBattery = bank[secondBatteryLocation]
        total += firstBattery * 10 + secondBattery
    }
    println(getElapsedTime())
    println(total)
}

fun findFirstBattery(bank: List<Int>): Int {
    var max = 0
    var location = 0
    for (i in 0..<bank.size - 1) {
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

fun findSecondBattery(bank: List<Int>, firstBatteryLocation: Int): Int {
    var max = 0
    var location = 0
    for (i in firstBatteryLocation + 1..<bank.size) {
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

fun parseInput1(input: String): List<List<Int>> = input
    .split("\n")
    .filter { it.isNotBlank() }
    .map { it.toCharArray() }
    .map { it.map { digit -> digit.digitToInt() } }