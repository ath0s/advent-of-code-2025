import Direction.SOUTH

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
                    listOf(below.move(Direction.WEST), below.move(Direction.EAST)).filter {
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

    override fun partTwo(filename: String, verbose: Boolean): Any {
        TODO("Not yet implemented")
    }

    companion object : Day.Main("Day07.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}