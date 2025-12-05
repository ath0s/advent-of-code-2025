import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.math.max
import kotlin.math.min


class Day05 : Day {
    override fun partOne(filename: String, verbose: Boolean): Int {
        val (freshRanges, ingredients) = filename.asPath().parseInventory()

        return ingredients.count { ingredient ->
            freshRanges.any { ingredient in it }
        }
    }

    override fun partTwo(filename: String, verbose: Boolean): Long {
        val (freshRanges, _) = filename.asPath().parseInventory()
        val sortedRanges = freshRanges.sortedBy { it.last }
        val merged = mergeOverlap(sortedRanges)
        return merged.sumOf { it.last - it.first + 1 }
    }

    private fun Path.parseInventory(): Pair<List<LongRange>, List<Long>> {
        var freshRangesDone = false

        val freshRanges = mutableListOf<LongRange>()
        val ingredients = mutableListOf<Long>()
        readLines().forEach { line ->
            when {
                line.isBlank() -> freshRangesDone = true
                freshRangesDone -> ingredients += line.toLong()
                else -> {
                    val (start, stop) = line.split("-")
                    freshRanges += start.toLong()..stop.toLong()
                }
            }
        }
        return freshRanges to ingredients
    }

    private fun mergeOverlap(ranges: List<LongRange>): List<LongRange> {
        val merged = mutableListOf<LongRange>()
        var current = ranges.first()
        for (i in 1..<ranges.size) {
            val range = ranges[i]
            if (current.first <= range.last && range.first <= current.last) {
                current = min(current.first, range.first)..max(current.last, range.last)
            } else {
                merged += current
                current = range
            }
        }
        merged += current
        return merged
    }

    companion object : Day.Main("Day05.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}