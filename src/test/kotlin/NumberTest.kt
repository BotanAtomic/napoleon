import org.junit.jupiter.api.Test

class NumberTest {

    @Test
    fun `integer computation`() {
        scriptAssert("4 * 5", 20)
        scriptAssert("10 ** -2", 0.01)
        scriptAssert("(-11 / 2)", -5.5)
        scriptAssert("(11 // 5)", 2)
        scriptAssert("11 % -5", 1)
        scriptAssert("11 << 1", 22)
        scriptAssert("0x6577E << 9", 212794368)
        scriptAssert("41 >> 1", 20)
        scriptAssert("-41 ^ 4", -45)
        scriptAssert("-12 & -9", -12)
        scriptAssert("-12 | -9", -9)
        scriptAssert("1 | 4", 5)
    }

    @Test
    fun `float computation`() {
        scriptAssert("4 * 5.0", 20.0)
        scriptAssert("10.0 ** -2.1", 0.007943282347242814)
        scriptAssert("(-11.0 / -0.2)", 55.0)
        scriptAssert("(11.0 // 5.0)", 2.0)
        scriptAssert("11.0 % 5.0", 1.0)
    }

}
