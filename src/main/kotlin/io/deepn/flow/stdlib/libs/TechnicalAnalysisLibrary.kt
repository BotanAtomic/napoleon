package io.deepn.flow.stdlib.libs

import io.deepn.flow.stdlib.Cached
import io.deepn.flow.stdlib.Filter
import io.deepn.flow.stdlib.Package
import io.deepn.flow.variables.ta.IndicatorVariable
import io.deepn.flow.variables.primitive.IntegerVariable
import io.deepn.flow.variables.ta.BarSeriesVariable
import org.ta4j.core.Indicator
import org.ta4j.core.indicators.CachedIndicator
import org.ta4j.core.indicators.EMAIndicator
import org.ta4j.core.indicators.SMAIndicator
import org.ta4j.core.indicators.helpers.ConstantIndicator
import org.ta4j.core.num.NaN
import org.ta4j.core.num.Num

class ArithmeticIndicator(
    private val source: Indicator<Num>,
    private val second: Indicator<Num>,
    private val operation: (Num, Num) -> Num
) : CachedIndicator<Num>(source) {

    constructor(source: Indicator<Num>, second: Number, operation: (Num, Num) -> Num) : this(
        source,
        ConstantIndicator(source.barSeries, source.numOf(second)),
        operation
    )

    override fun calculate(index: Int): Num {
        return if (source.getValue(index).isNaN || second.getValue(index).isNaN)
            NaN.NaN
        else
            operation(source.getValue(index), second.getValue(index))
    }
}

@Package("ta")
object TechnicalAnalysisLibrary {

    /** BarSeries **/

    fun BarSeriesVariable.open() = this.open
    fun BarSeriesVariable.high() = this.high
    fun BarSeriesVariable.low() = this.low
    fun BarSeriesVariable.close() = this.close
    fun BarSeriesVariable.volume() = this.volume

    /** Technical Indicators **/

    fun sma(source: IndicatorVariable, @Filter.StrictPositive period: IntegerVariable): IndicatorVariable = IndicatorVariable(
        SMAIndicator(source.value, period.toInt())
    )

    @Cached
    fun ema(source: IndicatorVariable, @Filter.StrictPositive period: IntegerVariable): IndicatorVariable = IndicatorVariable(
        EMAIndicator(source.value, period.toInt())
    )

}
