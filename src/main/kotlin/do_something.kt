fun main(args: Array<String>) {
    val lines = input()

    val claims = lines.map(::parseClaim)

    val peaceful = claims.filter { claim ->
        claims.none { otherClaim ->
            claim.intersects(otherClaim) && otherClaim != claim
        }
    }

    println(peaceful)
}

data class Claim(
    val id: String,
    val widthOffset: Int,
    val heightOffset: Int,
    val width: Int,
    val height: Int
) {
    fun contains(x: Int, y: Int) : Boolean {
        val withinX = x >= leftEdge() && x <= rightEdge()
        val withinY = y >= northEdge() && y <= southEdge()
        return withinX && withinY
    }

    fun leftEdge() = widthOffset

    fun rightEdge() = widthOffset + (width - 1)

    fun northEdge() = heightOffset

    fun southEdge() = heightOffset + (height - 1)

    fun intersects(claim: Claim) : Boolean {
        val tooConservative = leftEdge() > claim.rightEdge()
        val tooLiberal = rightEdge() < claim.leftEdge()
        val tooHoly = southEdge() < claim.northEdge()
        val tooEvil = northEdge() > claim.southEdge()

        val lackOfIntersection = tooConservative || tooLiberal || tooHoly || tooEvil

        return !lackOfIntersection
    }
}

class Point(val x: Int, val y: Int) {
    fun overClaimed(claims: List<Claim>) : Boolean {
        var claimCount = 0
        claims.forEach { claim ->
            if (claim.contains(x, y)) {
                claimCount += 1
                if (claimCount >= 2) {
                    return true
                }
            }
        }
        return false
    }
}

fun points(maxHeight: Int, maxWidth: Int) : List<Point> {
    val points = mutableListOf<Point>()
    for (x in 0..maxWidth) {
        for (y in 0..maxHeight) {
            points.add(Point(x, y))
        }
    }
    return points
}

fun parseClaim(line: String): Claim {
    val match = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)").matchEntire(line)!!

    val id = match.groups[1]!!.value
    val widthOffset = match.groups[2]!!.value.toInt()
    val heightOffset = match.groups[3]!!.value.toInt()
    val width = match.groups[4]!!.value.toInt()
    val height = match.groups[5]!!.value.toInt()

    return Claim(id, widthOffset, heightOffset, width, height)
}
