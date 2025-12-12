import java.nio.file.Path
import kotlin.io.path.readText

class Day12: Day {
    override fun partOne(filename: String, verbose: Boolean): Any {
        return filename.asPath().parseParagraphs().last().map { it.parseNumbers() }
            .count { g -> g.take(2).reduce(Int::times) >= g.drop(2).sumOf { it * 9 } }
    }

    override fun partTwo(filename: String, verbose: Boolean): Any {
        TODO("Not yet implemented")
    }

    private fun Path.parseParagraphs() =
        readText().split("\n\n").map { it.split("\n") }

    private fun String.parseNumbers() =
        split(Regex("\\D+")).filter { it.isNotBlank() }.map { it.toInt() }

    companion object : Day.Main("Day12.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}