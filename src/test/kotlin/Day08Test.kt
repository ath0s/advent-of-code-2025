import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test : DayTest<Day08>("Day08_test.txt") {
    override val partOneExpected = 40
    override val partTwoExpected = 25272

    @Test
    override fun `Part One`() {
        val result = target.connectBoxes(filenamePartOne!!, true, 10)

        assertEquals(partOneExpected, result)
    }
}
