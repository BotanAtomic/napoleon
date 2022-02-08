import org.junit.jupiter.api.Test

class VariableDeclarationTest {

    @Test
    fun `variable declaration`() {
        scriptAssert("""
            a = [1,2,[1,[-1],3]]
            b = "Hello"
            a[2][1][0] = 5
            a[0] = not true
            a[1] = b
            a[2][1][0]
        """.trimIndent(), 5)
    }

}
