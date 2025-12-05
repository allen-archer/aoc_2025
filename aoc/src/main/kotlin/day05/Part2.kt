package day05

import utils.*
import kotlin.math.max

fun main() {
    setInputIoStartTime()
    val input = getInput(5)
    setInputParseStartTime()
    parseInput1(input)
    setAlgorithmStartTime()
    var count = 0L
    ingredientRanges.sortBy { it.first }
    var i = 0
    while (i < ingredientRanges.size) {
        var j = i + 1
        while (j < ingredientRanges.size && doRangesOverlap(ingredientRanges[i], ingredientRanges[j])) {
            ingredientRanges[i] = LongRange(ingredientRanges[i].first, max(ingredientRanges[i].last, ingredientRanges[j].last))
            j++
        }
        count += ingredientRanges[i].last - ingredientRanges[i].first + 1
        i = j
    }
    println(getElapsedTime())
    println(count)
}

fun doRangesOverlap(left: LongRange, right: LongRange): Boolean = right.first in left || right.first - 1 == left.last