import org.jetbrains.annotations.VisibleForTesting
import kotlin.io.path.readLines
import kotlin.math.pow

class Day08 : Day {
    override fun partOne(filename: String, verbose: Boolean): Int =
        connectBoxes(filename, verbose, 1_000)

    override fun partTwo(filename: String, verbose: Boolean): Int =
        connectBoxes(filename, verbose, null)

    @VisibleForTesting
    internal fun connectBoxes(filename: String, verbose: Boolean, maxIterations: Int?): Int {
        val points = filename.asPath().readLines().map {
            val (x, y, z) = it.split(",")
            Coordinate3d(x.toInt(), y.toInt(), z.toInt())
        }
        val parents = points.associateByTo(mutableMapOf()) { it }
        val sizes = points.associateWithTo(mutableMapOf()) { 1 }

        fun union(a: Coordinate3d, b: Coordinate3d) {
            var a = parents.findUltimateParent(a)
            var b = parents.findUltimateParent(b)

            if (a == b) {
                return
            }
            if (sizes[a]!! > sizes[b]!!) {
                val c = a
                a = b
                b = c
            }
            parents[a] = b
            sizes.computeIfPresent(b) { _, previous -> previous + sizes[a]!! }
            sizes[a] = 0
        }

        val order = points.flatMapIndexed { index, i ->
            points.drop(index + 1).map { j ->
                Triple(squareDistance(i, j), i, j)
            }
        }.sortedBy { (distance) -> distance }

        if (maxIterations != null) {
            order.take(maxIterations).forEach { (_, i, j) ->
                union(i, j)
            }
            val descendingSizes = sizes.values.sortedDescending()
            return descendingSizes.take(3).reduce(Int::times)
        } else {
            order.forEach { (_, i, j) ->
                union(i, j)

                if(sizes[parents.findUltimateParent(i)] == points.size) {
                    return i.x * j.x
                }
            }
            throw IllegalStateException("Cannot resolve")
        }
    }

    companion object : Day.Main("Day08.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }

    private fun squareDistance(p1: Coordinate3d, p2: Coordinate3d) =
        (p2.x - p1.x).toLong().pow(2) + (p2.y - p1.y).toLong().pow(2) + (p2.z - p1.z).toLong().pow(2)

    private fun MutableMap<Coordinate3d, Coordinate3d>.findUltimateParent(coordinate3d: Coordinate3d): Coordinate3d {
        if (coordinate3d != get(coordinate3d)) {
            put(coordinate3d, findUltimateParent(get(coordinate3d)!!))
        }
        return get(coordinate3d)!!
    }

    private fun Long.pow(n: Int) =
        toDouble().pow(n).toLong()
}