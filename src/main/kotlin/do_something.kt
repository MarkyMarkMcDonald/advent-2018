import java.util.stream.Collectors

fun main(args: Array<String>) {
    val chars = input().first().toCharArray().toList()

    val withoutWorries = ('a'..'z').map { char ->
        chars.filterNot { it.equals(char, ignoreCase = true) }
    }

    val furtherReductions = withoutWorries.
        parallelStream().
        map(::fullyReduced).
        map(List<*>::size).
        collect(Collectors.toList())

    val lowestLength = furtherReductions.minBy { it }

    println(lowestLength)
}

private fun fullyReduced(chars: List<Char>): List<Char> {
    var chars1 = chars
    while (chars1.isReducable()) {
        chars1 = chars1.reducify()
    }
    return chars1
}

private fun List<Char>.reducify(): List<Char> {
    val reaction = this.reactors().indexOfFirst(::willReact)
    return subList(0, reaction).plus(subList(reaction + 2, size))
}

private fun List<Char>.isReducable(): Boolean = reactors().any(::willReact)

private fun List<Char>.reactors() = this.windowed(2)

private fun willReact(it: List<Char>): Boolean {
    val first = it[0]
    val second = it[1]

    val capsDoNotMatch = first.isLowerCase() && second.isUpperCase() ||
            first.isUpperCase() && second.isLowerCase()
    val sameChar = first.equals(second, ignoreCase = true)

    return capsDoNotMatch && sameChar
}
