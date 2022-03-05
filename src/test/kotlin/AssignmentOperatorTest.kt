import io.deepn.flow.error.NameError
import org.junit.jupiter.api.Test

class AssignmentOperatorTest {

    @Test
    fun plusAssign() {
        scriptAssertThrowable("a += 2", NameError::class)

        scriptAssert(
            """
            a = 1
            a += 3
            a
        """.trimIndent(), 4
        )
        scriptAssert(
            """
            a = 1
            a += 2.5
            a
        """.trimIndent(), 3.5
        )
        scriptAssert(
            """
            a = 1.0
            a += 3
            a
        """.trimIndent(), 4.0
        )
        scriptAssert(
            """
            a = 1
            a += -3
            a
        """.trimIndent(), -2
        )
        scriptAssert(
            """
            a = -1
            a += -3
            a
        """.trimIndent(), -4
        )
    }

    @Test
    fun minusAssign() {
        scriptAssertThrowable("""a -= 2""", NameError::class)
        scriptAssert(
            """
            a = 1
            a -= 3
            a
        """.trimIndent(), -2
        )
        scriptAssert(
            """
            a = 2.5
            a -= 2.5
            a
        """.trimIndent(), 0.0
        )
        scriptAssert(
            """
            a = 5.0
            a -= 3
            a
        """.trimIndent(), 2.0
        )
        scriptAssert(
            """
            a = 1
            a -= -3
            a
        """.trimIndent(), 4
        )
        scriptAssert(
            """
            a = -1
            a -= -3
            a
        """.trimIndent(), 2
        )
    }

    @Test
    fun divideAssign() {
        scriptAssertThrowable("""a /= 2""", NameError::class)
        scriptAssert(
            """
            a = 10
            a /= 2
            a
        """.trimIndent(), 5.0
        )
        scriptAssert(
            """
            a = 10
            a /= 3.0
            a
        """.trimIndent(), 3.3333333333333335
        )
        scriptAssert(
            """
            a = 10.0
            a /= 2
            a
        """.trimIndent(), 5.0
        )
        scriptAssert(
            """
            a = 10
            a /= -2
            a
        """.trimIndent(), -5.0
        )
        scriptAssert(
            """
            a = -10
            a /= -2
            a
        """.trimIndent(), 5.0
        )
    }

    @Test
    fun floorDivideAssign() {
        scriptAssertThrowable("""a //= 2""", NameError::class)
        scriptAssert(
            """
            a = 10
            a //= 2
            a
        """.trimIndent(), 5
        )
        scriptAssert(
            """
            a = 10
            a //= 3.0
            a
        """.trimIndent(), 3
        )
        scriptAssert(
            """
            a = 10.0
            a //= 2
            a
        """.trimIndent(), 5.0
        )
        scriptAssert(
            """
            a = 10
            a //= -2.0
            a
        """.trimIndent(), -5
        )
        scriptAssert(
            """
            a = -10.0
            a //= -2.0
            a
        """.trimIndent(), 5.0
        )
    }

    @Test
    fun multiplyAssign() {
        scriptAssertThrowable("""a *= 2""", NameError::class)
        scriptAssert(
            """
            a = 1
            a *= 3
            a
        """.trimIndent(), 3
        )
        scriptAssert(
            """
            a = 1
            a *= 2.5
            a
        """.trimIndent(), 2.5
        )
        scriptAssert(
            """
            a = 10.0
            a *= 2
            a
        """.trimIndent(), 20.0
        )
        scriptAssert(
            """
            a = 1
            a *= -3
            a
        """.trimIndent(), -3
        )
        scriptAssert(
            """
            a = -1
            a *= -3
            a
        """.trimIndent(), 3
        )
    }


}
