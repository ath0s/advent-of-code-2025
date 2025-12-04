class Day04 : Day {
    override fun partOne(filename: String, verbose: Boolean): Any {
        val diagram = filename.asPath().parseMatrix()

        val reachableCoordinates = mutableSetOf<Coordinate>()
        diagram.forEachIndexed { coordinate, char ->
            val neighbors = diagram.getAllNeighbors(coordinate)
            val reachable = neighbors.count { diagram[it] == '@' } < 4
            if (char == '@' && reachable) {
                reachableCoordinates += coordinate
            }
        }

        if (verbose) {
            diagram.print { it in reachableCoordinates }
        }
        return reachableCoordinates.count()
    }

    override fun partTwo(filename: String, verbose: Boolean): Any {
        val diagram = filename.asPath().parseMatrix()
        val removed = mutableSetOf<Coordinate>()
        do {
            var changed = false
            diagram.forEachIndexed { coordinate, char ->
                val neighbors = diagram.getAllNeighbors(coordinate)
                val reachable = neighbors.count { diagram[it] == '@' } < 4
                if (char == '@' && reachable) {
                    diagram[coordinate] = '.'
                    removed += coordinate
                    changed = true
                }
            }
        } while (changed)

        if (verbose) {
            diagram.print { it in removed }
        }
        return removed.count()
    }

    companion object : Day.Main("Day04.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}