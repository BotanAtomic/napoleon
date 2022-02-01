import org.junit.jupiter.api.Test

class LocalFunctionTest {

    @Test
    fun `local function`() {
        scriptAssert("""
            function test()
                return 1 + 1
            end
            
            test()
        """.trimIndent(), 2)

        scriptAssert("""
            function test(a, b)
                return (a + b)
            end
            
            test(1, 2)
        """.trimIndent(), 3)

        scriptAssert("""
            function test(a, b, c=4)
                return (a + b) // c
            end
            
            _1 = test(6, 2) == 2
            _2 = test(6, 2, 8) == 1
            _3 = test(c=6, b=2, a=8) == 1
            _1 and _2 and _3
        """.trimIndent(), true)

        scriptAssert("""
            k = 4
            function test(a, b, c=4)
                return ((a + b) // c) + k
            end
            
            _1 = test(6, 2) == 2 + k
            _2 = test(6, 2, 8) == 1 + k
            _3 = test(c=6, b=2, a=8) == 1 + k
            _1 and _2 and _3
        """.trimIndent(), true)

        scriptAssert("""
            k = 4
            z = {1,2,3}
            function test(a, b, c=4)
                z[0] = -1
                return ((a + b) // c) + k
            end
            _0 = z[0] == 1
            _1 = test(6, 2) == 2 + k
            _2 = test(6, 2, 8) == 1 + k
            _3 = test(c=6, b=2, a=8) == 1 + k
            _0 and _1 and _2 and _3 and z[0] == -1
        """.trimIndent(), true)
    }

}
