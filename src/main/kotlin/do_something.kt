fun main(args: Array<String>) {
    val pointsOfInterest: List<PointOfInterest> = input().map(::parsePointOfInterest)

    var count = 0

    (-32..464).forEach { x ->
        (-32..464).forEach { y ->
            val distanceSums = pointsOfInterest.distancesSum(x, y)
            if (distanceSums < 10000) count += 1
        }
    }

    println(count)
}

private fun List<PointOfInterest>.distancesSum(x: Int, y: Int): Int {
    return this.sumBy { it.manhattanDistance(x, y) }
}

fun parsePointOfInterest(line: String): PointOfInterest {
    val x = line.split(", ")[0].toInt()
    val y = line.split(", ")[1].toInt()
    return PointOfInterest(x, y)
}

data class PointOfInterest(val x: Int, val y: Int) {
    fun manhattanDistance(x2: Int, y2: Int): Int {
        return Math.abs(x - x2) + Math.abs(y - y2)
    }
}
