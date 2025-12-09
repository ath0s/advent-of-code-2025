import kotlin.io.path.readLines

class Day09 : Day {
    override fun partOne(filename: String, verbose: Boolean): Long {
        val redTiles = filename.asPath().readLines().map {
            val (x, y) = it.split(",")
            Coordinate(x.toInt(), y.toInt())
        }
        if (verbose) {
            val minX = redTiles.minOf { it.x }
            val maxX = redTiles.maxOf { it.x }
            val minY = redTiles.minOf { it.y }
            val maxY = redTiles.maxOf { it.y }

            (minY..maxY).forEach { y ->
                (minX..maxX).forEach { x ->
                    if (Coordinate(x, y) in redTiles) {
                        print('#')
                    } else {
                        print('.')
                    }
                }
                println()
            }
        }
        var largestRectangle = 0L

        redTiles.forEachIndexed { index, firstTile ->
            redTiles.drop(index + 1).forEach { secondTile ->
                val ySize = maxOf(firstTile.y, secondTile.y) - minOf(firstTile.y, secondTile.y) + 1
                val xSize = maxOf(firstTile.x, secondTile.x) - minOf(firstTile.x, secondTile.x) + 1
                val size = ySize.toLong() * xSize.toLong()
                if (size > largestRectangle) {
                    largestRectangle = size
                }
            }
        }
        return largestRectangle
    }

    override fun partTwo(filename: String, verbose: Boolean): Any {
        TODO("Not yet implemented")
    }

    companion object : Day.Main("Day09.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}