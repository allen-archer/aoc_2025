package day09

import utils.*
import kotlin.math.abs

fun main() {
    setInputIoStartTime()
    val input = getInput(9)
    setInputParseStartTime()
    val polygon = parseInput1(input)
    setAlgorithmStartTime()
    val rectangles: MutableList<Rectangle> = mutableListOf()
    for (i in polygon.indices) {
        for (j in i + 1..<polygon.size) {
            rectangles.add(Rectangle(polygon[i], polygon[j]))
        }
    }
    rectangles.sortByDescending { it.area }
    var answer: Rectangle? = null
    for (rectangle in rectangles) {
        if (isInsidePolygon(rectangle.corner3, polygon)
            && isInsidePolygon(rectangle.corner4, polygon)
        ) {
            val edgePoints: MutableList<Pair<Long, Long>> = mutableListOf()
            edgePoints.addAll(getAllPointsBetween(rectangle.corner1, rectangle.corner3))
            edgePoints.addAll(getAllPointsBetween(rectangle.corner1, rectangle.corner4))
            edgePoints.addAll(getAllPointsBetween(rectangle.corner2, rectangle.corner3))
            edgePoints.addAll(getAllPointsBetween(rectangle.corner2, rectangle.corner4))
            var isInside = true
            for (edgePoint in edgePoints) {
                if (!isInsidePolygon(edgePoint, polygon)) {
                    isInside = false
                    break
                }
            }
            if (isInside) {
                answer = rectangle
                break
            }
        }
    }
    println(getElapsedTime())
    println(answer?.area ?: "Whoops")
}

data class Rectangle(val corner1: Pair<Long, Long>, val corner2: Pair<Long, Long>) {
    val corner3: Pair<Long, Long> = corner1.first to corner2.second
    val corner4: Pair<Long, Long> = corner2.first to corner1.second
    val area: Long = (1 + abs(corner1.first - corner2.first)) * (1 + abs(corner1.second - corner2.second))
}

fun isInsidePolygon(point: Pair<Long, Long>, polygon: List<Pair<Long, Long>>): Boolean {
    val x = point.first
    val y = point.second
    var intersectionCount = 0L
    for (i in polygon.indices) {
        val p1 = polygon[i]
        val p2 = polygon[(i + 1) % polygon.size]
        if (point == p1 || point == p2 || isOnEdge(point, p1, p2)) {
            return true
        }
        val y1 = p1.second
        val y2 = p2.second
        val crossesRay = (y in y1..<y2) || (y in y2..<y1)
        if (crossesRay) {
            val x1 = p1.first
            val x2 = p2.first
            val xIntersect = (x2 - x1) * (y - y1).toDouble() / (y2 - y1) + x1
            if (x < xIntersect) {
                intersectionCount++
            }
        }
    }
    return intersectionCount % 2L != 0L
}

fun isOnEdge(p: Pair<Long, Long>, p1: Pair<Long, Long>, p2: Pair<Long, Long>): Boolean {
    val crossProduct = (p.second - p1.second) * (p2.first - p1.first) -
            (p.first - p1.first) * (p2.second - p1.second)
    if (crossProduct != 0L) {
        return false
    }
    val isBetweenX = p.first in minOf(p1.first, p2.first)..maxOf(p1.first, p2.first)
    val isBetweenY = p.second in minOf(p1.second, p2.second)..maxOf(p1.second, p2.second)
    return isBetweenX && isBetweenY
}

fun getAllPointsBetween(left: Pair<Long, Long>, right: Pair<Long, Long>): List<Pair<Long, Long>> {
    if (right.first == left.first) {
        val max = maxOf(left.second, right.second)
        val min = minOf(left.second, right.second)
        return (min + 1..<max).toList().map { right.first to it }
    } else if (right.second == left.second) {
        val max = maxOf(left.first, right.first)
        val min = minOf(left.first, right.first)
        return (min + 1..<max).toList().map { it to right.second }
    } else {
        throw Exception("Whoops")
    }
}