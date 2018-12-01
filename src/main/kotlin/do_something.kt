import java.io.File

fun main(args: Array<String>) {
    val lines = File("src/main/resources/input.txt").inputStream().bufferedReader().readLines()
    val numbers = lines.map(Integer::parseInt)
    val result = numbers.sum()
    println(result)
}