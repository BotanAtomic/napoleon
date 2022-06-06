package esgi.napoleon.variables.ta

import esgi.napoleon.error.IndexError
import esgi.napoleon.error.TypeError
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.primitive.FloatVariable
import esgi.napoleon.variables.primitive.IntegerVariable
import esgi.napoleon.variables.primitive.ListVariable
import esgi.napoleon.variables.primitive.ObjectVariable
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

    override fun valueToString(): String {
        return "Series of ${value.barCount} bars"
    }

    override fun getIndex(position: Variable<*>): Variable<*> {
        if (position is IntegerVariable) {
            val index = position.toInt()
            if (index < 0 || index > value.endIndex)
                throw IndexError("bar series index out of range: index=${index}, size=${value.barCount}")

            val bar = value.getBar(index)
            return ObjectVariable(hashMapOf(
                    "open" to FloatVariable(bar.openPrice),
                    "high" to FloatVariable(bar.highPrice),
                    "low" to FloatVariable(bar.lowPrice),
                    "close" to FloatVariable(bar.closePrice),
                    "volume" to FloatVariable(bar.volume)
                ))

        }
        throw TypeError("indicator index must be integer, not '${position.type()}'")
    }

    override fun toIterator(): Iterator<Variable<*>> {
        return value.barData.map { ObjectVariable(hashMapOf(
                "open" to FloatVariable(it.openPrice),
                "high" to FloatVariable(it.highPrice),
                "low" to FloatVariable(it.lowPrice),
                "close" to FloatVariable(it.closePrice),
                "volume" to FloatVariable(it.volume)
            )) }.iterator()
    }
}
