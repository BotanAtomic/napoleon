import io.deepn.flow.error.TypeError
import org.junit.jupiter.api.Test

class LambdaFunctionTest {

    @Test
    fun lambda() {
        scriptAssertThrowable(
            """
            list = [1, 2, 3, 4]
            function test(a)
                return 10-a
            end
            
            newList = list.map(function x -> test(b=x))
        """.trimIndent(), TypeError::class
        )
        scriptAssert(
            """
            list = [1, 2, 3, 4]
            function test(a)
                return 10-a
            end
            
            newList = list.map(function x -> test(a=x))
            newList
        """.trimIndent(), listOf(9,8,7,6)
        )
        scriptAssert(
            """
            list = [1, 2, 3, 4]
            function test(a, b = 0)
                return b-a
            end
            
            newList = list.map(function x -> test(b=x, a=10))
            newList
        """.trimIndent(), listOf(-9,-8,-7,-6)
        )
    }

}
