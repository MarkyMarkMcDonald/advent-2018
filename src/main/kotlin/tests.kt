fun main(args: Array<String>) {
    val claim1 = Claim("1", 1, 1, 4, 4)
    val claim2 = Claim("2", 3, 3, 4, 4)
    println(claim1.contains(0, 0))


    println(Point(3, 3).overClaimed(listOf(claim1, claim2)))
    println(Point(4, 4).overClaimed(listOf(claim1, claim2)))
}