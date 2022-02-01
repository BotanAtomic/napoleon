import org.junit.jupiter.api.Test

class ConditionTest {

    @Test
    fun condition() {
        scriptAssert("""
            a = 0
            b = true
            
            if b then
                a = 'c'
            end
            a
        """.trimIndent(), "c")

        scriptAssert("""
            a = 0
            b = false
            
            if b then
                a = 'c'
            end
            a
        """.trimIndent(), 0)

        scriptAssert("""
            a = 0
            b = false
            
            if b then
                a = 'c'
            else
                a = 'else'
            end
            a
        """.trimIndent(), "else")

        scriptAssert("""
            a = 0
            b = true
            c = false
            
            if c then
                a = 'c'
            elseif not b then
                a = 'b'
            elseif b or c then
                a = 'a or b'
            end
            a
        """.trimIndent(), "a or b")
    }

}
