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
            storage.init("versionList", [])
            a = version
            storage.get("versionList").insert(0, a)
            
            if version >= 9 then
                return storage.get("versionList")[9] == version - 9
            end
        """.trimIndent(), true, 30)
    }


}
