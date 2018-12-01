import java.io.File

fun main(args: Array<String>) {
    val lines = File("src/main/resources/input.txt").inputStream().bufferedReader().readLines()
    val numbers = lines.map(Integer::parseInt)

    var currentFrequency = 0
    val pastFrequencies = mutableSetOf(currentFrequency)
    var repeatedFrequency: Int? = null
    var index = 0

    while (repeatedFrequency == null) {
        if (index >= numbers.size) {
            index = 0
        }

        val number = numbers[index]

        val nextFrequency = currentFrequency + number
        val added = pastFrequencies.add(nextFrequency)
        currentFrequency = nextFrequency

        if (!added) {
            repeatedFrequency = nextFrequency
        }

        index += 1
    }

    println(repeatedFrequency)
}