import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val pastFrequencies = mutableSetOf(0)

    frequencyChanges().reduce { currentFrequency, change ->
        val nextFrequency = currentFrequency + change

        val unique = pastFrequencies.add(nextFrequency)
        if (!unique) {
            println(nextFrequency)
            exitProcess(0)
        }

        nextFrequency
    }
}

fun frequencyChanges(): Sequence<Int> {
    val lines = File("src/main/resources/input.txt").inputStream().bufferedReader().readLines()
    return lines.map(Integer::parseInt).toCycle()
}

fun <T : Any> List<T>.toCycle(): Sequence<T> {
    var index = -1

    return generateSequence {
        if (index + 1 >= this.size) {
            index = -1
        }
        index += 1
        this[index]
    }
}
