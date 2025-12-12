package day12

import utils.*

fun main() {
    setInputIoStartTime()
    val input = getInput(12)
    setInputParseStartTime()
    val (gifts, regions) = parseInput1(input)
    setAlgorithmStartTime()
    var total = 0
    for (i in regions.indices) {
        val totalAreaOfGifts = regions[i].gifts.mapIndexed { index, count -> count * gifts[index].area }.sum()
        if (totalAreaOfGifts <= regions[i].area) {
            total++
        }
    }
    println(getElapsedTime())
    println(total)
}

data class Gift(val shape: List<List<Char>>, val area: Int)

data class Region(val width: Int, val height: Int, val area: Int, val gifts: List<Int>)

fun parseInput1(input: String): Pair<List<Gift>, List<Region>> {
    val gifts: MutableList<Gift> = mutableListOf()
    val regions: MutableList<Region> = mutableListOf()
    val lines = input.lines()
    lines.forEachIndexed { index, line ->
        if (line.contains(":")) {
            if (line.contains("x")) {
                val split = line.split(" ")
                val shape = split.first().replace(":", "").split("x")
                val width = shape.first().toInt()
                val height = shape.last().toInt()
                regions.add(
                    Region(
                        width,
                        height,
                        width * height,
                        split.slice(1..<split.size).map { str -> str.toInt() })
                )
            } else {
                var nextBlankLineIndex = 0
                for (i in index + 1..<lines.size) {
                    if (lines[i].isBlank()) {
                        nextBlankLineIndex = i
                        break
                    }
                }
                val shape = lines.slice(index + 1..<nextBlankLineIndex).map { it.toCharArray().toList() }
                val area = shape.flatten().filter { it == '#' }.size
                gifts.add(Gift(shape, area))
            }
        }
    }
    return gifts to regions
}