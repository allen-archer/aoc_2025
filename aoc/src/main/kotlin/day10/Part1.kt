package day10

import utils.*

val possiblePermutations = generatePermutations(4, 10)

fun main() {
    setInputIoStartTime()
    val input = getInput(10)
    setInputParseStartTime()
    val machines = parseInput1(input)
    setAlgorithmStartTime()
    var total = 0
    for (machine in machines) {
        val graph = Graph(machine)
        var vertices =
            mutableListOf(graph.vertices[possiblePermutations[machine.configuration.size]!!.first().hashCode()]!!)
        val visited: MutableSet<Int> = mutableSetOf(vertices.first().key)
        var steps = 0
        loop@ while (vertices.isNotEmpty()) {
            steps++
            val nextVertices: MutableList<Vertex> = mutableListOf()
            for (vertex in vertices) {
                for (nextVertex in vertex.edges) {
                    if (nextVertex.configuration == machine.configuration) {
                        break@loop
                    } else if (!visited.contains(nextVertex.key)) {
                        nextVertices.add(nextVertex)
                        visited.add(nextVertex.key)
                    }
                }
            }
            vertices = nextVertices
        }
        total += steps
    }
    println(getElapsedTime())
    println(total)
}

fun generatePermutations(min: Int, max: Int): Map<Int, List<List<Boolean>>> {
    val results = mutableMapOf<Int, List<List<Boolean>>>()
    for (size in min..max) {
        val permutationsForSize = generatePermutationsBinary(size)
        results[size] = permutationsForSize
    }
    return results
}

private fun generatePermutationsBinary(size: Int): List<List<Boolean>> {
    val numberOfPermutations = 1 shl size
    val permutations = mutableListOf<List<Boolean>>()
    for (i in 0 until numberOfPermutations) {
        val currentPermutation = mutableListOf<Boolean>()
        for (j in 0 until size) {
            val bitPosition = size - 1 - j
            val isBitSet = (i shr bitPosition) and 1 == 1
            currentPermutation.add(isBitSet)
        }
        permutations.add(currentPermutation)
    }
    return permutations
}

data class Machine(val configuration: List<Boolean>, val buttons: List<List<Int>>, val joltages: List<Int>)

data class Graph(val machine: Machine) {
    val vertices: Map<Int, Vertex> = possiblePermutations[machine.configuration.size]!!.associate {
        val vertex = Vertex(it)
        vertex.key to vertex
    }

    init {
        for (vertex in vertices.values) {
            for (button in machine.buttons) {
                val nextVertex = processButtonPress(vertex, button)
                if (vertex.configuration != nextVertex) {
                    val vertexHash = nextVertex.hashCode()
                    vertex.addEdge(vertices[vertexHash]!!)
                }
            }
        }
    }
}

data class Vertex(val configuration: List<Boolean>) {
    val key = configuration.hashCode()
    val edges: MutableList<Vertex> = mutableListOf()
    fun addEdge(edge: Vertex) {
        edges.add(edge)
    }
}

fun processButtonPress(vertex: Vertex, button: List<Int>): List<Boolean> {
    return vertex.configuration.mapIndexed { i, it -> if (button.contains(i)) !it else it }
}

fun parseInput1(input: String): List<Machine> {
    return input.lines()
        .filter { it.isNotBlank() }
        .map {
            val split = it.split(" ")
            val configuration = split.first().toCharArray()
                .slice(1..<split.first().length - 1)
                .map { char ->
                    when (char) {
                        '#' -> true
                        else -> false
                    }
                }
            val buttons = split.slice(1..<split.size - 1)
                .map { str ->
                    str.slice(1..<str.length - 1)
                        .split(",")
                        .map { number -> number.toInt() }
                }
            val joltages = split.last()
                .slice(1..<split.last().length - 1)
                .split(",")
                .map { number -> number.toInt() }
            Machine(configuration, buttons, joltages)
        }
}