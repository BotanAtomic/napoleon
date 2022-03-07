package mocks

import org.ta4j.core.BarSeries
import org.ta4j.core.Indicator
import org.ta4j.core.num.Num


class MockIndicator(private val series: BarSeries, private val values: List<Num>) : Indicator<Num> {


    override fun getValue(index: Int): Num {
        return values[index]
    }

    override fun getBarSeries(): BarSeries {
        return series
    }

    override fun numOf(number: Number): Num {
        return series.numOf(number)
    }
}
