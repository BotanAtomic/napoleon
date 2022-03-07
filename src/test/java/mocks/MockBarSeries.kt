package mocks

import org.ta4j.core.Bar
import org.ta4j.core.BaseBarSeries
import org.ta4j.core.num.Num
import java.time.ZonedDateTime
import java.util.function.Function


/**
 * A bar series with sample data. TODO: add Builder
 */
class MockBarSeries : BaseBarSeries {
    constructor(nf: Function<Number, Num>, vararg data: Number) : super(numbersToBar(nf, listOf(*data)))
    constructor(nf: Function<Number, Num>, data: List<Double>) : super(numbersToBar(nf, data))
    constructor(bars: List<Bar?>?) : super(bars)
    constructor(nf: Function<Number, Num>, data: DoubleArray, times: Array<ZonedDateTime>) : super(
        doublesAndTimesToBars(nf, data, times)
    )

    constructor(nf: Function<Number, Num>, vararg dates: ZonedDateTime) : super(timesToBars(nf, *dates))
    constructor(nf: Function<Number, Num>) : super(arbitraryBars(nf))

    companion object {
        private fun numbersToBar(nf: Function<Number, Num>, data: List<Number>): List<Bar> {
            val bars = ArrayList<Bar>()
            for (i in data.indices) {
                bars.add(MockBar(ZonedDateTime.now().minusSeconds((data.size + 1 - i).toLong()), data[i].toDouble(), nf))
            }
            return bars
        }


        private fun doublesAndTimesToBars(
            nf: Function<Number, Num>,
            data: DoubleArray,
            times: Array<ZonedDateTime>
        ): List<Bar> {
            require(data.size == times.size)
            val bars = ArrayList<Bar>()
            for (i in data.indices) {
                bars.add(MockBar(times[i], data[i], nf))
            }
            return bars
        }

        private fun timesToBars(nf: Function<Number, Num>, vararg dates: ZonedDateTime): List<Bar> {
            val bars = ArrayList<Bar>()
            var i = 1
            for (date in dates) {
                bars.add(MockBar(date, (i++).toDouble(), nf))
            }
            return bars
        }

        private fun arbitraryBars(nf: Function<Number, Num>): List<Bar> {
            val bars = ArrayList<Bar>()
            for (i in 0..4999) {
                val j = i.toDouble()
                bars.add(
                    MockBar(
                        ZonedDateTime.now().minusMinutes((5001 - i).toLong()),
                        j,
                        j + 1,
                        j + 2,
                        j + 3,
                        j + 4,
                        j + 5,
                        (i + 6).toLong(),
                        nf
                    )
                )
            }
            return bars
        }
    }
}
