fun main(args: Array<String>) {
    var chars = input().first().toCharArray().toList()

    while (chars.isReducable()) {
        chars = chars.reducify()
    }

    println(chars.size)
}

private fun List<Char>.reducify(): List<Char> {
    val reaction = this.reactors().indexOfFirst(::willReact)
    return toList().subList(0, reaction).plus(toList().subList(reaction + 2, size))
}

private fun List<Char>.isReducable(): Boolean = reactors().any(::willReact)

private fun List<Char>.reactors() = this.toList().windowed(2)

private fun willReact(it: List<Char>): Boolean {
    if (it.size != 2) {
        throw IllegalArgumentException()
    }

    val first = it[0]
    val second = it[1]

    val capsDoNotMatch = first.isLowerCase() && second.isUpperCase() ||
            first.isUpperCase() && second.isLowerCase()
    val sameChar = first.equals(second, ignoreCase = true)

    return capsDoNotMatch && sameChar
}
