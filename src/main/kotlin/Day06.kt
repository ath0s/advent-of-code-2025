import kotlin.io.path.readLines



class Day06 : Day {
    override fun partOne(filename: String, verbose: Boolean): Long {
        val lines = filename.asPath().readLines()
        val numbers = lines.dropLast(1)
            .mapToArray { line ->
                Regex("""\d+""")
                    .findAll(line)
                    .map { it.value.toLong() }
                    .toList()
                    .toTypedArray()
            }.rotate()

        val operators = lines.last()
            .let { line ->
                Regex("""\S""").findAll(line)
                    .map { it.value[0] }
                    .toList()
                    .reversed()
                    .toTypedArray()
            }

        if (verbose) {
            numbers.forEachIndexed { index: Int, values ->
                print(values.joinToString(" "))
                println(" ${operators[index]}")
            }
        }

        return numbers.withIndex().fold(0L) { sum: Long, indexedValue ->
            val (index, values) = indexedValue
            sum + when (operators[index]) {
                '+' -> values.reduce(Long::plus)
                '*' -> values.reduce(Long::times)
                else -> throw IllegalArgumentException("Unhandled operator")
            }
        }
    }

    override fun partTwo(filename: String, verbose: Boolean): Any {
        TODO("Not yet implemented")
    }

    companion object : Day.Main("Day06.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}