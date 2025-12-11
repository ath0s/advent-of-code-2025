import kotlin.io.path.readLines

class Day11: Day {
    override fun partOne(filename: String, verbose: Boolean): Long  =
        filename.asPath().readLines().recurseThroughInstruction("you")

    override fun partTwo(filename: String, verbose: Boolean): Long =
        filename.asPath().readLines().recurseThroughInstruction("svr", listOf("fft", "dac"))

    private val knownPaths = mutableMapOf<String, Long>()

    private fun List<String>.recurseThroughInstruction(node: String, mustVisit: List<String> = emptyList()): Long {
        if (node == "out") return if (mustVisit.isEmpty()) 1 else 0
        val next = first { it.startsWith("$node: ") }.substringAfter("$node: ").split(" ")
        if (knownPaths[node + mustVisit] != null) return knownPaths[node + mustVisit]!!
        val pathLength = next.fold(0L) { acc, s -> acc + recurseThroughInstruction(s, mustVisit - s) }
        knownPaths[node + mustVisit] = pathLength
        return pathLength
    }

    companion object : Day.Main("Day11.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}