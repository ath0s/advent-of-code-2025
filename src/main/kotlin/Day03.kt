import kotlin.io.path.readLines

class Day03 : Day {
    override fun partOne(filename: String, verbose: Boolean): Long {
        val banks = filename.asPath().readLines()
        val maxJoltage = banks.map { bank ->
            bank.maxJoltages(2).toLong()
        }
        if (verbose) {
            maxJoltage.forEach {
                println(it)
            }
        }
        return maxJoltage.sum()
    }

    override fun partTwo(filename: String, verbose: Boolean): Long {
        val banks = filename.asPath().readLines()
        val maxJoltage = banks.map { bank ->
            bank.maxJoltages(12).toLong()
        }
        if (verbose) {
            maxJoltage.forEach {
                println(it)
            }
        }
        return maxJoltage.sum()
    }

    private fun String.maxJoltages(numberOfBatteries: Int): String {
        if (numberOfBatteries == 0) {
            return ""
        }
        if (length == numberOfBatteries) {
            return this
        }
        var maxValue = '0'
        var indexOfFirstMax = -1
        substring(0, length - numberOfBatteries + 1).forEachIndexed { index, bank ->
            if (bank > maxValue) {
                maxValue = bank
                indexOfFirstMax = index
            }
        }
        return maxValue.toString() + substring(indexOfFirstMax + 1).maxJoltages(numberOfBatteries - 1)
    }

    companion object : Day.Main("Day03.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}