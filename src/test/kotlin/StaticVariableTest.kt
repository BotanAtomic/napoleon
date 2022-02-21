import io.deepn.script.error.NameError
import org.junit.jupiter.api.Test

class StaticVariableTest {

    @Test
    fun `static variable init`() {
        scriptAssert("""
            static a = 0
            a += 1
            return a
        """.trimIndent(), 4, 4)
    }

    @Test
    fun `duplicate static init`() {
        scriptAssertThrowable("""
            static a = 0
            static a = 2
            a += 1
            return a
        """.trimIndent(), NameError::class, 4)
    }

}
