package day05

import utils.*

var ingredientRanges: MutableList<LongRange> = mutableListOf()
var ingredients: MutableList<Long> = mutableListOf()

fun main() {
    setInputIoStartTime()
    val input = getInput(5)
    setInputParseStartTime()
    parseInput1(input)
    setAlgorithmStartTime()
    var count = 0
    for (ingredient in ingredients) {
        for (ingredientRange in ingredientRanges) {
            if (ingredient in ingredientRange) {
                count++
                break
            }
        }
    }
    println(getElapsedTime())
    println(count)
}

fun parseInput1(input: String) {
    val split = input.split("\n\n")
    split[0].lines()
        .filter { it.isNotBlank() }
        .forEach {
            val range = it.split("-")
            ingredientRanges.add(LongRange(range[0].toLong(), range[1].toLong()))
        }
    split[1].lines()
        .filter { it.isNotBlank() }
        .forEach { ingredients.add(it.toLong()) }
}