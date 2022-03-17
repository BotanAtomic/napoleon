import org.junit.jupiter.api.Test
import java.util.*

class NativeLibraryTest {

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
            mList = [1,2,3]
            mList.reverse()
        """.trimIndent(), LinkedList(listOf(3,2,1))
        )

        scriptAssert("""
            mList = [1,2,3]
            mList.clear()
            mList
        """.trimIndent(), LinkedList<Any>()
        )
    }

    @Test
    fun `built in variables`() {
        scriptAssert("math.PI", 3.141592653589793)
    }

}
