package io.deepn.flow.variables.ta

import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.primitive.IntegerVariable
import org.ta4j.core.BarSeries
import org.ta4j.core.indicators.helpers.*

class BarSeriesVariable(series: BarSeries) : Variable<BarSeries>(series) {

    val open = IndicatorVariable(OpenPriceIndicator(value))
    val high = IndicatorVariable(HighPriceIndicator(value))
    val low = IndicatorVariable(LowPriceIndicator(value))
    val close = IndicatorVariable(ClosePriceIndicator(value))
    val volume = IndicatorVariable(VolumeIndicator(value))

    override fun type() = "bar_series"

    override fun length() = IntegerVariable(value.barCount)

}
