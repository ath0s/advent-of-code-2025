class Day03 : Day {
    override fun partOne(filename: String, verbose: Boolean): Any {
        val banks = filename.asPath().parseMatrix()
        val maxJoltage = banks.map { bank ->
            bank.foldIndexed(0L) { index, acc, current ->
                if (index < bank.lastIndex) {
                    bank.drop(index + 1).fold(acc) { acc, next ->
                        maxOf(acc, "$current$next".toLong())
                    }
                } else {
                    acc
                }
            }
        }
        if (verbose) {
            maxJoltage.forEach {
                println(it)
            }
        }
        return maxJoltage.sum()
    }

    override fun partTwo(filename: String, verbose: Boolean): Any {
        TODO("Not yet implemented")
    }

    companion object : Day.Main("Day03.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}