import Direction.EAST
import Direction.SOUTH
import Direction.WEST


class Day07 : Day {
    override fun partOne(filename: String, verbose: Boolean): Int {
        val diagram = filename.asPath().parseMatrix()
        val start = diagram.find { it == 'S' } ?: throw IllegalArgumentException("Cannot find S")

        var next = setOf(start)
        val usedSplits = mutableSetOf<Coordinate>()
        loop@ while (true) {
            next = next.flatMapToSet { coordinate ->
                val below = coordinate.move(SOUTH)
                if (below !in diagram) {
                    break@loop
                }
                if (diagram[below] == '^') {
                    usedSplits += below
                    listOf(below.move(WEST), below.move(EAST)).filter {
                        it in diagram && diagram[it] != '^'
                    }
                } else {
                    listOf(below)
                }
            }
            next.forEach { diagram[it] = '|' }

        }

        if (verbose) {
            diagram.print { it in usedSplits }
        }

        return usedSplits.count()
    }

    override fun partTwo(filename: String, verbose: Boolean): Long {
        val diagram = filename.asPath().parseMatrix()
        var splits = mapOf(diagram.find { it == 'S' }!! to 1L)
        loop@ while (true) {
            splits = buildMap {
                splits.forEach { (coordinate, count) ->
                    val below = coordinate.move(SOUTH)
                    if (below !in diagram) {
                        break@loop
                    }
                    if (diagram[below] == '^') {
                        val left = below.move(WEST)
                        this[left] = (this[below.move(WEST)] ?: 0) + count

                        val right = below.move(EAST)
                        this[right] = (this[right] ?: 0) + count
                    } else {
                        this[below] = (this[below] ?: 0) + count
                    }
                }
            }
        }
        return splits.values.sum()
    }

    companion object : Day.Main("Day07.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}