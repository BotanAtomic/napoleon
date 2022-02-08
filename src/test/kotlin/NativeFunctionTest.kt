import org.junit.jupiter.api.Test
import java.util.*

class NativeFunctionTest {

    @Test
    fun `native function`() {
        scriptAssert("""
            print("Hello", "World", "\n")
        """.trimIndent(), Any())

        scriptAssert("""
            math.sin(4)
        """.trimIndent(), -0.7568024953079282)

        scriptAssert("""
            println("normal",math.randomInt())
            println("between 0 and 10", math.randomInt(0, 11))
            println("between 1 and 2", math.randomInt(1, 3))
            println("to 3", math.randomInt(to=4))
            println("from 4", math.randomInt(from=4))
            println("between 0 and 3", math.randomInt(to=4, from=0))
        """.trimIndent(), Any())

        scriptAssert("""
            len([1,2,3])
        """.trimIndent(), 3)
    }

    @Test
    fun `function extension`() {
        scriptAssert("""
            list = [1,2,3]
            list.reverse()
        """.trimIndent(), LinkedList(listOf(3,2,1))
        )

        scriptAssert("""
            list = [1,2,3]
            list.clear()
            list
        """.trimIndent(), LinkedList<Any>()
        )
    }

}
