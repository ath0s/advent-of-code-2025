import kotlin.io.path.readLines


class Day06 : Day {
    override fun partOne(filename: String, verbose: Boolean): Long =
        calculate(filename, verbose) { lines ->
            lines.mapToArray { line ->
                Regex("""\d+""")
                    .findAll(line)
                    .map { it.value.toLong() }
                    .toList()
                    .toTypedArray()
            }.rotate()
        }

    override fun partTwo(filename: String, verbose: Boolean): Long =
        calculate(filename, verbose) { lines ->
            val maxLength = lines.maxOf { it.length }
            lines.map { it.padEnd(maxLength) }
                .parseMatrix()
                .rotate()
                .fold(mutableListOf(mutableListOf<Long>())) { problems, chars ->
                    val line = String(chars.toCharArray())
                    if (line.isBlank()) {
                        problems += mutableListOf<Long>()
                    } else {
                        problems.last() += line.trim().toLong()
                    }
                    problems
                }.mapToArray { it.toTypedArray() }
        }

    private fun calculate(filename: String, verbose: Boolean, parseNumbers: (List<String>) -> Array<Array<Long>>): Long {
        val lines = filename.asPath().readLines()
        val numbers = parseNumbers(lines.dropLast(1))

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

    companion object : Day.Main("Day06.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}