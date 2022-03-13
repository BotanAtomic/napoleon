package ta

import io.deepn.flow.DefaultExecutionEnvironment
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.scope.impl.DefaultScope
import io.deepn.flow.variables.primitive.BooleanVariable
import io.deepn.flow.variables.primitive.FloatVariable
import io.deepn.flow.variables.ta.BarSeriesVariable
import mocks.MockBarSeries
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.ta4j.core.BaseBarSeries
import org.ta4j.core.num.DecimalNum
import org.ta4j.core.num.Num
import java.util.function.Function

class SMAIndicatorTest {

    private val numFunction = Function<Number, Num> { t -> DecimalNum.valueOf(t) }

    private val data = MockBarSeries(numFunction, 1, 2, 3, 4, 3, 4, 5, 4, 3, 3, 4, 3, 2)

    @Test
    fun `test single pass`() {

        val code = """
            sma_3 = ta.sma(series.close(), 3)
            
            matches = [1, 1.5, 2, 3 , 10/3, 11/3, 4, 13/3, 4, 10/3, 10/3, 10/3, 3].reverse() 
            
            for i=0, len(series) -1 do
                result = sma_3[i] == matches[i]
                if not result then
                    println(i, "-> expecting", matches[i], "but got", sma_3[i])
                    return false
                end
            end
            return true
        """.trimIndent()

        val scope = DefaultScope(
            initialVariables = mutableMapOf(
                "series" to BarSeriesVariable(data)
            ) as VariableMap
        )

        val environment = DefaultExecutionEnvironment(code, scope)
        assert(environment.compile().success)
        val result = environment.execute().let {
            if(!it.success) println(it.error)
            assert(it.success)
            it.result
        }
        assert(result is BooleanVariable && result.value)
    }

    @Test
    fun `test multiple pass`() {

        val code = "ta.sma(series.close(), 3)[0]"

        val newData = BaseBarSeries("sma_test")

        val scope = DefaultScope(
            initialVariables = mutableMapOf(
                "series" to BarSeriesVariable(newData)
            ) as VariableMap
        )

        val environment = DefaultExecutionEnvironment(code, scope)
        assert(environment.compile().success)
        val expectedResults = listOf(
            1.0,
            1.5,
            2.0,
            3.0,
            10.0 / 3.0,
            11.0 / 3.0,
            4.0,
            13.0 / 3.0,
            4.0,
            10.0 / 3.0,
            10.0 / 3.0,
            10.0 / 3.0,
            3.0
        )
        data.barData.forEachIndexed { index, bar ->
            newData.addBar(bar)
            val result = environment.execute().result as FloatVariable
            Assertions.assertEquals(expectedResults[index], result.value)
        }

    }
}
