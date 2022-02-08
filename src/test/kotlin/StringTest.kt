import org.junit.jupiter.api.Test

class StringTest {


    @Test
    fun `string initialisation`() {
        scriptAssert("'He\n\tLo'", "He\n\tLo")


        val longStringTest = "\"\"\"Lol mdr\"\"\""
        scriptAssert("""
            $longStringTest
        """.trimIndent(), "Lol mdr")
    }


}
