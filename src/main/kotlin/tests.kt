fun main(args: Array<String>) {
    println("INTERSECTIONS")

    // . . . .
    // . 1 1 .
    // . 1 x .
    // . . . .
    var claim1 = Claim("1", 1, 1, 2, 2)
    var claim2 = Claim("2", 2, 2, 1, 1)
    println("1.1, ${claim1.intersects(claim2)}; ${claim2.intersects(claim1)}")

    // . . . .
    // . 1 1 .
    // . 1 x 2
    // . . 2 2
    claim1 = Claim("1", 1, 1, 2, 2)
    claim2 = Claim("2", 2, 2, 2, 2)
    println("2.1, ${claim1.intersects(claim2)}; ${claim2.intersects(claim1)}")

    // . . . .
    // . . 2 2
    // . 1 x 2
    // . 1 1 .
    claim1 = Claim("1", 1, 2, 2, 2)
    claim2 = Claim("2", 2, 1, 2, 2)
    println("2.2, ${claim1.intersects(claim2)}; ${claim2.intersects(claim1)}")

    // . . . .
    // . 1 1 .
    // . x x .
    // . 2 2 .
    claim1 = Claim("1", 1, 1, 2, 2)
    claim2 = Claim("2", 1, 2, 2, 2)
    println("2.3, ${claim1.intersects(claim2)}; ${claim2.intersects(claim1)}")

    // . . . .
    // . 1 x 2
    // . 1 x 2
    // . . . .
    claim1 = Claim("1", 1, 1, 2, 2)
    claim2 = Claim("2", 2, 1, 2, 2)
    println("2.4, ${claim1.intersects(claim2)}; ${claim2.intersects(claim1)}")

    // . . 2 2 .
    // . 1 x 2 .
    // . 1 x 2 .
    // . . 2 2 .
    claim1 = Claim("1", 1, 1, 2, 2)
    claim2 = Claim("2", 2, 0, 2, 4)
    println("2.5, ${claim1.intersects(claim2)}; ${claim2.intersects(claim1)}")

    println("NON-INTERSECTIONS")

    // 1 1 . .
    // 1 1 . .
    // 2 2 . .
    // 2 2 . .
    claim1 = Claim("1", 0, 0, 2, 2)
    claim2 = Claim("2", 0, 2, 2, 2)
    println("3.1, ${claim1.intersects(claim2)}; ${claim2.intersects(claim1)}")

    // 1 1 2 2
    // 1 1 2 2
    // . . . .
    // . . . .
    claim1 = Claim("1", 0, 0, 2, 2)
    claim2 = Claim("2", 2, 0, 2, 2)
    println("3.2, ${claim1.intersects(claim2)}; ${claim2.intersects(claim1)}")


    // 1 1 . .
    // 1 1 2 2
    // . . 2 2
    // . . . .
    claim1 = Claim("1", 0, 0, 2, 2)
    claim2 = Claim("2", 2, 1, 2, 2)
    println("3.3, ${claim1.intersects(claim2)}; ${claim2.intersects(claim1)}")

    // 1 1 . .
    // 1 1 . .
    // . 2 2 .
    // . 2 2 .
    claim1 = Claim("1", 0, 0, 2, 2)
    claim2 = Claim("2", 1, 2, 2, 2)
    println("3.3, ${claim1.intersects(claim2)}; ${claim2.intersects(claim1)}")
}