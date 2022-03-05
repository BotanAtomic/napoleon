import io.deepn.flow.error.NameError
import org.junit.jupiter.api.Test

class VersioningTest {

    @Test
    fun `incremental index`() {
        scriptAssert("version", 3, 4)
    }

    @Test
    fun `assign index`() {
        scriptAssertThrowable("version = 4", NameError::class)
    }

    @Test
    fun `incremental variable index`() {
        scriptAssert("""
            a = version
 
            history(a, 9) == version - 9
        """.trimIndent(), true, 30)
    }


}
