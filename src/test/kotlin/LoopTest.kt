import org.junit.jupiter.api.Test
import java.util.*

class LoopTest {

    @Test
    fun `while loop`() {
        scriptAssert("""
            a = true
            i = 0
            while i != 100 do
                b = i + 1
                i = b
            end
            i
        """.trimIndent(), 100)

        scriptAssert("""
            a = true
            i = 0
            while a do
                i = i + 1
                a = i < 400000
            end
            i
        """.trimIndent(), 400000 )

        scriptAssert("""
            a = 10

            --[ repeat loop execution --]
            repeat
               a = a + 1
            until( a > 15 )

            a
        """.trimIndent(), 16)
    }

    @Test
    fun `for loop`() {
        scriptAssert("""
            a = 0
            for i = 0, 11 do
                a = i
            end
            a
        """.trimIndent(), 11)

        scriptAssert("""
            a = 0
            for i = 0, 11, 1 do
                a = i
            end
            a
        """.trimIndent(), 11)

        scriptAssert("""
            a = 0
            for i = 100, 50, -1 do
                a = i
            end
            a
        """.trimIndent(), 50)

    }

    @Test
    fun `foreach loop`() {
        scriptAssert("""
            a = 0
            value = "Hello"
            for i in value do
                a = i
            end
            a
        """.trimIndent(), "o")

        scriptAssert("""
            a = ["H", 1, 9, "P"]
            b = [0,0,0,0]
            j = 0
            for i in a do
                b[j] = i
                j = j + 1
            end
            b
        """.trimIndent(), LinkedList(listOf("H", 1, 9, "P"))
        )
    }


}
