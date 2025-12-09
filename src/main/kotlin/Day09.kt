import kotlin.io.path.readLines

class Day09 : Day {
    override fun partOne(filename: String, verbose: Boolean): Long {
        val redTiles = filename.asPath().readLines().map {
            val (x, y) = it.split(",")
            LongCoordinate(x.toLong(), y.toLong())
        }
        if (verbose) {
            val minX = redTiles.minOf { it.x }
            val maxX = redTiles.maxOf { it.x }
            val minY = redTiles.minOf { it.y }
            val maxY = redTiles.maxOf { it.y }

            (minY..maxY).forEach { y ->
                (minX..maxX).forEach { x ->
                    if (LongCoordinate(x, y) in redTiles) {
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
                val rectangle = Rectangle(firstTile, secondTile)
                val size = rectangle.size
                if (size > largestRectangle) {
                    largestRectangle = size
                }
            }
        }
        return largestRectangle
    }

    override fun partTwo(filename: String, verbose: Boolean): Long {
        val redTiles = filename.asPath().readLines().map {
            val (x, y) = it.split(",")
            LongCoordinate(x.toLong(), y.toLong())
        }

        val lines = redTiles.mapIndexed { index, a ->
            Line(a, redTiles[(index + 1) % redTiles.size])
        }

        var max = 0L
        redTiles.forEachIndexed { index, firstTile ->
            var localMax = 0L
            redTiles.drop(index + 1).forEach { secondTile ->
                val probe = Rectangle(firstTile, secondTile).size
                if (probe > localMax) {
                    if (lines.none { it intersects Line(firstTile, secondTile) }) {
                        localMax = probe
                    }
                }
            }
            max = maxOf(max, localMax)
        }
        return max
    }

    private infix fun Line.intersects(line2: Line): Boolean {
        return line2.maxX > minX && line2.minX < maxX && line2.maxY > minY && line2.minY < maxY
    }

    private data class Line(val a: LongCoordinate, val b: LongCoordinate) {
        val minX = minOf(a.x, b.x)
        val maxX = maxOf(a.x, b.x)
        val minY = minOf(a.y, b.y)
        val maxY = maxOf(a.y, b.y)
    }

    private data class Rectangle(val a: LongCoordinate, val b: LongCoordinate) {
        val width = maxOf(a.x, b.x) - minOf(a.x, b.x)
        val height = maxOf(a.y, b.y) - minOf(a.y, b.y)
        val size = (width + 1) * (height + 1)
    }

    companion object : Day.Main("Day09.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}