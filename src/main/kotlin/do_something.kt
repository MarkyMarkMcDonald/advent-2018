import java.io.File

fun main(args: Array<String>) {
    val numbers = frequencyChanges().iterator()

    var currentFrequency = 0
    val pastFrequencies = mutableSetOf(currentFrequency)
    var repeatedFrequency: Int? = null

    while (repeatedFrequency == null) {
        val number = numbers.next()

        val nextFrequency = currentFrequency + number
        val added = pastFrequencies.add(nextFrequency)
        currentFrequency = nextFrequency

        if (!added) {
            repeatedFrequency = nextFrequency
        }
    }

    println(repeatedFrequency)
}

fun frequencyChanges(): Sequence<Int> {
    val lines = File("src/main/resources/input.txt").inputStream().bufferedReader().readLines()
    return lines.map(Integer::parseInt).toCycle()
}

fun <T : Any> List<T>.toCycle (): Sequence<T> {
    var index = -1

    return generateSequence {
        if (index + 1 >= this.size) {
            index = -1
        }
        index += 1
        this[index]
    }
}
