import java.io.File

fun main(args: Array<String>) {
    val twoErs = lines().filter(::twoEr).size
    val threeErs = lines().filter(::threeEr).size
    val checksum = twoErs * threeErs
    println(checksum)
}

fun twoEr(boxId: String): Boolean {
    val charCounts: Map<Char, Int> = boxId.toCharArray().groupBy { it }.mapValues { it.value.size }
    return charCounts.values.any { it == 2 }
}

fun threeEr(boxId: String): Boolean {
    val charCounts: Map<Char, Int> = boxId.toCharArray().groupBy { it }.mapValues { it.value.size }
    return charCounts.values.any { it == 3 }
}

fun lines() : List<String> = File("src/main/resources/input.txt").inputStream().bufferedReader().readLines()
