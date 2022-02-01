import org.junit.jupiter.api.Test

class StringTest {


    @Test
    fun `string initialisation`() {
        scriptAssert("'He\n\tLo'", "He\n\tLo")
        scriptAssert("""
            [[Lol mdr]]
        """.trimIndent(), "Lol mdr")
    }


}
