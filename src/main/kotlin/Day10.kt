import com.microsoft.z3.Context
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status.SATISFIABLE
import java.util.PriorityQueue
import kotlin.io.path.readLines

class Day10 : Day {
    override fun partOne(filename: String, verbose: Boolean): Int {
        val machines = filename.asPath().readLines().map { it.parseMachine() }

        if (verbose) {
            machines.forEach { machine ->
                print(machine.indicatorLights.joinToString("", "[", "]") { if (it) "#" else "." })
                print(" ")
                print(machine.buttons.joinToString(" ") { it.joinToString(",", "(", ")") })
                print(" ")
                print(machine.joltages.joinToString(",", "{", "}"))
                println()
            }
        }

        return machines.sumOf { machine ->
            search(
                List(machine.indicatorLights.size) { false },
                { state ->
                    machine.buttons.map { buttons ->
                        state.mapIndexed { index, on ->
                            if (index in buttons) !on else on
                        } to 1
                    }
                },
                { state -> state == machine.indicatorLights}
            )[machine.indicatorLights]!!
        }
    }

    override fun partTwo(filename: String, verbose: Boolean): Int {
        val machines = filename.asPath().readLines().map { it.parseMachine() }

        return machines.sumOf { machine ->
            Context().use { ctx ->
                val solver = ctx.mkOptimize()
                val zero = ctx.mkInt(0)

                val buttons = machine.buttons.indices
                    .map { ctx.mkIntConst("button#$it") }
                    .onEach { button -> solver.Add(ctx.mkGe(button, zero)) }
                    .toTypedArray()

                machine.joltages.forEachIndexed { counter, targetValue ->
                    val buttonsThatIncrement = machine.buttons
                        .withIndex()
                        .filter { (_, counters) -> counter in counters }
                        .mapToArray { (index) -> buttons[index] }
                    val target = ctx.mkInt(targetValue)
                    val sumOfPresses = ctx.mkAdd(*buttonsThatIncrement)
                    solver.Add(ctx.mkEq(sumOfPresses, target))
                }

                val presses = ctx.mkIntConst("presses")
                solver.Add(ctx.mkEq(presses, ctx.mkAdd(*buttons)))
                solver.MkMinimize(presses)

                if (solver.Check() != SATISFIABLE) error("No solution found for machine: $machine.")
                solver.model.evaluate(presses, false).let { it as IntNum }.int
            }
        }
    }

    private fun <T : Any> search(
        start: T,
        neighbours: (T) -> Iterable<Pair<T, Int>>,
        goalFunction: (T) -> Boolean,
        maximumCost: Int = Int.MAX_VALUE,
    ): Map<T, Int> {
        val distance = mutableMapOf(start to 0)
        val visited = mutableSetOf<Pair<T, Int>>()

        val queue = PriorityQueue(compareBy<Triple<T, Int, Int>> { (_, _, priority) -> priority })
        queue.add(Triple(start, 0, 0))

        while (queue.isNotEmpty()) {
            val (node, costSoFar, _) = queue.poll()
            if (!visited.add(node to costSoFar)) continue
            if (goalFunction(node)) return distance

            for ((nextNode, nextCost) in neighbours(node)) {
                if (maximumCost - nextCost < costSoFar) continue

                val totalCost = costSoFar + nextCost

                if (totalCost > (distance[nextNode] ?: Int.MAX_VALUE)) continue

                distance[nextNode] = totalCost

                queue.add(Triple(nextNode, totalCost, totalCost))
            }
        }

        return emptyMap()
    }

    private data class Machine(
        val indicatorLights: List<Boolean>,
        val buttons: List<Set<Int>>,
        val joltages: List<Int>,
    )

    private val machinePattern = Regex("""\[(.*)] (.*) \{(.*)}""")

    private fun String.parseMachine() =
        machinePattern.matchEntire(this)!!.destructured.let { (indicatorLights, buttons, joltages) ->
            Machine(
                indicatorLights.map { it == '#' },
                buttons.split(" ").map { button ->
                    button.removeSurrounding("(", ")").split(',').mapToSet { it.toInt() }
                },
                joltages.split(",").map { it.toInt() }
            )
        }

    companion object : Day.Main("Day10.txt") {
        @JvmStatic
        fun main(args: Array<String>) = main()
    }
}