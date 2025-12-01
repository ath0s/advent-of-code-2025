import Day01.Rotation.Left
import Day01.Rotation.Right
import kotlin.io.path.readLines

class Day01 : Day {
    private val start = 50

    override fun partOne(filename: String, verbose: Boolean): Int {
        val rotations = filename.asPath().readLines().map { line ->
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

        var current = start

        if (verbose) {
            println("The dial starts by pointing at $current")
        }

        var zeroCount = 0
        rotations.forEach { rotation ->
            current = rotation.rotate(current)
            if (verbose) {
                println("The dial is rotated $rotation to point at $current")
            }
            if (current == 0) {
                zeroCount++
            }
        }

        return zeroCount
    }

    override fun partTwo(filename: String, verbose: Boolean): Any {
        TODO("Not yet implemented")
    }

    private sealed interface Rotation {
        fun rotate(current: Int): Int

        data class Left(private val steps: Int) : Rotation {
            override fun rotate(current: Int): Int {
                var next = current - steps
                while (next < 0) {
                    next += 100
                }
                return next
            }

            override fun toString() = "L$steps"
        }

        data class Right(private val steps: Int) : Rotation {
            override fun rotate(current: Int): Int {
                var next = current + steps
                while (next > 99) {
                    next -= 100
                }
                return next
            }

            override fun toString() = "R$steps"
        }
    }

    companion object : Day.Main("Day01.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}