import java.io.File

fun main(args: Array<String>) {
    val lines = input()
    val offByOnes = lines.map { line -> offByOnes(lines, line) }.find { it != null }
    offByOnes!!

    val samesies = offByOnes.first.zip(offByOnes.second).filter { it.first == it.second }.map { it.first }.joinToString("")

    println(offByOnes.first)
    println(offByOnes.second)
    println(samesies)
}

fun offByOnes(lines: List<String>, line: String): Pair<String, String>? {
    val otherLine = lines.find { otherLine -> isOffByOne(line, otherLine) }
    if (otherLine != null) {
        return Pair(line, otherLine)
    }
    return null
}

fun isOffByOne(a: String, b: String): Boolean = a.zip(b).count { it.first != it.second } == 1

fun input(): List<String> = File("src/main/resources/input.txt").inputStream().bufferedReader().readLines()
