package day10

import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import com.google.ortools.linearsolver.MPVariable
import utils.*


fun main() {
    setInputIoStartTime()
    val input = getInput(10)
    setInputParseStartTime()
    val machines = parseInput1(input)
    setAlgorithmStartTime()
    Loader.loadNativeLibraries()
    var total = 0
    for (machine in machines) {
        val buttons = machine.buttons
        val joltages = machine.joltages
        val solver = MPSolver.createSolver("SCIP") ?: throw Exception("Could not create solver")
        val x = arrayOfNulls<MPVariable>(machine.buttons.size)
        for (i in buttons.indices) {
            x[i] = solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "x$i")
        }
        val target = joltages.map { it.toDouble() }
        val aMatrix = joltages.indices.map { joltageToArray(it, buttons) }.toTypedArray()
        for (j in joltages.indices) {
            val ct = solver.makeConstraint(target[j], target[j], "joltage_constraint_$j")
            for (i in buttons.indices) {
                ct.setCoefficient(x[i], aMatrix[j][i])
            }
        }
        val objective = solver.objective()
        for (i in buttons.indices) {
            objective.setCoefficient(x[i], 1.0)
        }
        objective.setMinimization()
        val resultStatus = solver.solve()
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
            val result = objective.value().toInt()
            total += result
        } else {
            println("Problem could not be solved.")
        }
    }
    println(getElapsedTime())
    println(total)
}

fun joltageToArray(joltageIndex: Int, buttons: List<List<Int>>): Array<Double> {
    val array = DoubleArray(buttons.size) { 0.0 }.toTypedArray()
    for (i in buttons.indices) {
        if (joltageIndex in buttons[i]) {
            array[i] = 1.0
        }
    }
    return array
}