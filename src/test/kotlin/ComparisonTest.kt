import org.junit.jupiter.api.Test

class ComparisonTest {

    @Test
    fun comparison() {
        scriptAssert("4.1 > 2", true)
        scriptAssert("10.0 < -2.1", false)
        scriptAssert("1.0 == 1.0", true)
        scriptAssert("0.9999999 != 1.00", true)
        scriptAssert("4.0 <= 4.0", true)
        scriptAssert("4.0 < 4.0", false)
        scriptAssert("true != false", true)
        scriptAssert("true != true", false)
        scriptAssert("false == false", true)
        scriptAssert("true != false", true)
        scriptAssert("true and false", false)
        scriptAssert("true or false", true)
        scriptAssert("{1,2,3} == {1,2,3}", true)
        scriptAssert("'bbb' > 'aaa'", true)
        scriptAssert("'aaa' == 'aaa'", true)
    }

}
