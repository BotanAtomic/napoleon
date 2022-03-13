package ta

import io.deepn.flow.DefaultExecutionEnvironment
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.scope.impl.DefaultScope
import io.deepn.flow.variables.primitive.BooleanVariable
import io.deepn.flow.variables.primitive.FloatVariable
import io.deepn.flow.variables.ta.BarSeriesVariable
import mocks.MockBar
import mocks.MockBarSeries
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.ta4j.core.BaseBarSeries
import org.ta4j.core.num.DecimalNum
import org.ta4j.core.num.Num
import java.util.function.Function
import kotlin.system.measureTimeMillis


class EMAIndicatorTest {

    private val numFunction = Function<Number, Num> { t -> DecimalNum.valueOf(t) }

    private val data = MockBarSeries(
        numFunction, 64.75, 63.79, 63.73, 63.73, 63.55, 63.19, 63.91, 63.85, 62.95, 63.37,
        61.33, 61.51
    )

    @Test
    fun `test single pass`() {

        val code = """
            ema_10 = ta.ema(series.close(), 10)
            
            matches = [63.694826748355545, 63.26485824865454, 62.94579311253553].reverse() 
            
            for i=0, 2 do
                result = ema_10[i] == matches[i]
                if not result then
                    println(i, "-> expecting", matches[i], "but got", ema_10[i])
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
        environment.compile()
        val execution = environment.execute()
        if (execution.success.not()) println(execution.error)
        assert(execution.success)
        val result = execution.result
        assert(result is BooleanVariable && result.value)
    }

    @Test
    fun `test multiple pass`() {

        val code = "ta.ema(series.close(), 10)[0]"

        val newData = BaseBarSeries("ema_test")

        val scope = DefaultScope(
            initialVariables = mutableMapOf(
                "series" to BarSeriesVariable(newData)
            ) as VariableMap
        )

        val environment = DefaultExecutionEnvironment(code, scope)
        assert(environment.compile().success)
        val expectedResults = listOf(63.694826748355545, 63.26485824865454, 62.94579311253553)
        data.barData.forEachIndexed { index, bar ->
            newData.addBar(bar)
            if (index >= 9) {
                val result = environment.execute().result as FloatVariable
                Assertions.assertEquals(expectedResults[index - 9], result.value)
            }
        }

    }

    @Test
    fun stackOverflowError() {

        val code = """
            ema_10 = ta.ema(series.close(), 10)
            ema_5 = ta.ema(series.close(), 5)
            
            if version % 2 == 0 then
                return ema_5[0]
            else
                return ema_10[0]
            end
        """.trimIndent()


        for (j in 0..3) {
            val bigSeries = MockBarSeries(ArrayList())
            val scope = DefaultScope(
                initialVariables = mutableMapOf(
                    "series" to BarSeriesVariable(bigSeries)
                ) as VariableMap,
            )
            val environment = DefaultExecutionEnvironment(code, scope)
            assert(environment.compile().success)
            val time = measureTimeMillis {
                for (i in 0..9999) {
                    bigSeries.addBar(MockBar(i, i.toDouble(), numFunction))
                    val result = environment.execute().let {
                        assert(it.success)
                        it.result
                    }
                    if (i == 9999) {
                        assert(result?.eq(FloatVariable(9994.5))?.value == true)
                    } else if (i == 9998) {
                        assert(result?.eq(FloatVariable(9996.0))?.value == true)
                    }
                }
            }

            println("Time: ${time}ms")
            assert(time < 5000) { "script execution is too slow" }
        }
    }
}
