package mocks

import org.ta4j.core.BaseBar
import org.ta4j.core.num.Num
import java.time.Duration
import java.time.ZonedDateTime
import java.util.function.Function


class MockBar : BaseBar {
    private var trades: Long = 0

    constructor(closePrice: Double, numFunction: Function<Number, Num>) : this(
        ZonedDateTime.now(),
        closePrice,
        numFunction
    )

    constructor(closePrice: Double, volume: Double, numFunction: Function<Number, Num>) : super(
        Duration.ofDays(1),
        ZonedDateTime.now(),
        0.0,
        0.0,
        0.0,
        closePrice,
        volume,
        0.0,
        0,
        numFunction
    )

    constructor(
        endTime: ZonedDateTime,
        closePrice: Double,
        numFunction: Function<Number, Num>
    ) : super(Duration.ofDays(1), endTime, 0.0, 0.0, 0.0, closePrice, 0.0, 0.0, 0, numFunction)

    constructor(
        endTime: ZonedDateTime,
        closePrice: Double,
        volume: Double,
        numFunction: Function<Number, Num>
    ) : super(
        Duration.ofDays(1), endTime, 0.0, 0.0, 0.0, closePrice, volume, 0.0, 0, numFunction
    )

    constructor(
        openPrice: Double, closePrice: Double, highPrice: Double, lowPrice: Double,
        numFunction: Function<Number, Num>
    ) : super(
        Duration.ofDays(1), ZonedDateTime.now(), openPrice, highPrice, lowPrice, closePrice, 1.0, 0.0, 0,
        numFunction
    )

    constructor(
        openPrice: Double, closePrice: Double, highPrice: Double, lowPrice: Double, volume: Double,
        numFunction: Function<Number, Num>
    ) : super(
        Duration.ofDays(1), ZonedDateTime.now(), openPrice, highPrice, lowPrice, closePrice, volume, 0.0, 0,
        numFunction
    )

    constructor(
        endTime: ZonedDateTime, openPrice: Double, closePrice: Double, highPrice: Double, lowPrice: Double,
        amount: Double, volume: Double, trades: Long, numFunction: Function<Number, Num>
    ) : super(Duration.ofDays(1), endTime, openPrice, highPrice, lowPrice, closePrice, volume, amount, 0, numFunction) {
        this.trades = trades
    }

    override fun getTrades(): Long {
        return trades
    }

}
