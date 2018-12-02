fun main(args: Array<String>) {
    val lines = input()

    val offByOnes: Pair<String, String> = offByOnes(lines)!!

    val samesies = offByOnes.commonLetters()

    println(samesies)
}

fun offByOnes(lines: List<String>): Pair<String, String>? {
    lines.forEach { line ->
        lines.forEach { otherLine ->
            when {
                isOffByOne(line, otherLine) -> return Pair(line, otherLine)
            }
        }
    }
    return null
}

fun isOffByOne(a: String, b: String) = a.zip(b).count { it.first != it.second } == 1

fun Pair<String, String>.commonLetters(): String {
    return this
        .zip()
        .filter(Pair<Any, Any>::equal)
        .map(Pair<Char, Char>::first)
        .joinToString("")
}
