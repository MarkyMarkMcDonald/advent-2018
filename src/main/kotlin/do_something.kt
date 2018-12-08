fun main(args: Array<String>) {
    val pointsOfInterest: List<PointOfInterest> = input().map(::parsePointOfInterest)

    val theWholeWorld = (0..400).map { x ->
        (0..400).map { y ->
            val minimums = pointsOfInterest.minimums(x, y)
            val closest = if (minimums.size == 1) {
                minimums.first()
            } else {
                null
            }
            Coordinate(x, y, closest)
        }
    }

    val northPole = (0..400).map { theWholeWorld[it][0] }
    val southPole = (0..400).map { theWholeWorld[it][400] }
    val northAmerica = (0..400).map { theWholeWorld[0][it] }
    val australia = (0..400).map { theWholeWorld[400][it] }

    val toInfinities: List<PointOfInterest> =
        (northPole + southPole + northAmerica + australia).
            mapNotNull(Coordinate::closestPointOfInterest)

    val theKnownWorld = theWholeWorld.flatten().filterNot { toInfinities.contains(it.closestPointOfInterest) }

    val asia: Int = theKnownWorld.filter { it.closestPointOfInterest != null }.groupBy(Coordinate::closestPointOfInterest).values.maxBy { it.size }!!.size

    println(asia)
}

private fun List<PointOfInterest>.minimums(x: Int, y: Int): List<PointOfInterest> {
    var minimums: List<PointOfInterest> = mutableListOf()
    var minValue = Integer.MAX_VALUE

    for (point in this) {
        val distance = point.manhattanDistance(x, y)
        if (distance == minValue) {
            minimums += point
        } else if (distance < minValue) {
            minValue = distance
            minimums = mutableListOf(point)
        }
    }
    return minimums
}

fun parsePointOfInterest(line: String): PointOfInterest {
    val x = line.split(", ")[0].toInt()
    val y = line.split(", ")[1].toInt()
    return PointOfInterest(x, y)
}

data class Coordinate(val x: Int, val y: Int, val closestPointOfInterest: PointOfInterest?)

data class PointOfInterest(val x: Int, val y: Int) {
    fun manhattanDistance(x2: Int, y2: Int): Int {
        return Math.abs(x - x2) + Math.abs(y - y2)
    }
}
