import kotlin.io.path.readText

class Day02 : Day {
    private val twiceRepeatPattern = Regex("""^(.*)\1$""")
    private val atLeastTwiceRepeatPattern = Regex("""^(.*)(\1)+$""")

    override fun partOne(filename: String, verbose: Boolean): Long =
        twiceRepeatPattern.sumDuplicates(filename, verbose)

    override fun partTwo(filename: String, verbose: Boolean) =
        atLeastTwiceRepeatPattern.sumDuplicates(filename, verbose)

    private fun Regex.sumDuplicates(filename: String, verbose: Boolean): Long {
        val ranges = filename.asPath().readText().split(',').map {
            val (start, end) = it.split('-')
            (start.toLong()..end.toLong())
        }
        if (verbose) {
            ranges.forEach { range ->
                println("${range.first}-${range.last}")
            }
        }
        val duplicates = ranges.flatMap { range ->
            range.mapNotNull { number ->
                number.takeIf { it.toString().matches(this) }
            }
        }
        if (verbose) {
            duplicates.forEach {
                println("duplicate=$it")
            }
        }

        return duplicates.sum()
    }

    companion object : Day.Main("Day02.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}