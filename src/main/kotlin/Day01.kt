import Day01.Rotation.Left
import Day01.Rotation.Right
import kotlin.io.path.readLines
import kotlin.math.absoluteValue

class Day01 : Day {
    private val start = 50

    override fun partOne(filename: String, verbose: Boolean): Int {
        val rotations = filename.parseRotations(verbose)

        return rotations
            .map { it.movement }
            .runningFold(start, Int::plus)
            .count { it % 100 == 0 }
    }

    override fun partTwo(filename: String, verbose: Boolean): Any {
        val rotations = filename.parseRotations(verbose)

        return rotations
            .flatMap {
                (1..(it.movement).absoluteValue).map { _ ->
                    when(it) {
                        is Left -> -1
                        is Right -> 1
                    }
                }
            }
            .runningFold(start, Int::plus)
            .count { it % 100 == 0 }
    }

    private fun String.parseRotations(verbose: Boolean): List<Rotation> {
        val rotations = asPath().readLines().map { line ->
            when (line.first()) {
                'L' -> Left(line.drop(1).toInt())
                'R' -> Right(line.drop(1).toInt())
                else -> throw IllegalArgumentException("Unexpected line $line")
            }
        }

        if (verbose) {
            rotations.forEach {
                println(it)
            }
        }

        return rotations
    }

    private sealed interface Rotation {
        val movement: Int

        data class Left(val distance: Int) : Rotation {
            override val movement = -distance
            override fun toString() = "L$distance"
        }

        data class Right(override val movement: Int) : Rotation {
            override fun toString() = "R$movement"
        }
    }

    companion object : Day.Main("Day01.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}