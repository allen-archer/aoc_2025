package day11

import utils.*

val problemVertices = "dac" to "fft"
val cache: MutableMap<Pair<String, Pair<Boolean, Boolean>>, Long> = mutableMapOf()

fun main() {
    setInputIoStartTime()
    val input = getInput(11)
    setInputParseStartTime()
    map = parseInput1(input)
    setAlgorithmStartTime()
    val total = countPaths2("svr", false to false).first
    println(getElapsedTime())
    println(total)
}

fun countPaths2(vertex: String, problemVerticesEncountered: Pair<Boolean, Boolean>): Pair<Long, Pair<Boolean, Boolean>> {
    val otherVertices = map!![vertex]!!
    var total = 0L
    val nextProblemVerticesEncountered =
        (problemVerticesEncountered.first || vertex == problemVertices.first) to (problemVerticesEncountered.second || vertex == problemVertices.second)
    for (otherVertex in otherVertices) {
        val key = otherVertex to nextProblemVerticesEncountered
        if (cache.contains(key)) {
            total += cache[key]!!
        } else if (otherVertex == end) {
            if (nextProblemVerticesEncountered.first && nextProblemVerticesEncountered.second) {
                total++
            }
        } else {
            total += countPaths2(otherVertex, nextProblemVerticesEncountered).first
        }
    }
    cache[vertex to nextProblemVerticesEncountered] = total
    return total to nextProblemVerticesEncountered
}