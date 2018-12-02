import java.io.File

fun Pair<CharSequence, CharSequence>.zip() = this.first.zip(this.second)

fun Pair<Any, Any>.equal() = this.first == this.second

fun input(): List<String> = File("src/main/resources/input.txt").inputStream().bufferedReader().readLines()