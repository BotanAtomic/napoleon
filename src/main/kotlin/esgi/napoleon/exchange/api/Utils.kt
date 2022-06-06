package esgi.napoleon.exchange.api

import esgi.napoleon.exchange.api.entity.Candle
import esgi.napoleon.exchange.api.entity.CurrencyPair
import esgi.napoleon.exchange.api.entity.TimeFrame
import org.knowm.xchange.currency.Currency
import org.ta4j.core.Bar
import org.ta4j.core.BarSeries
import org.ta4j.core.BaseBar
import org.ta4j.core.BaseBarSeriesBuilder
import org.ta4j.core.num.DecimalNum
import java.time.Duration
import java.time.ZoneId

fun CurrencyPair.toUnifiedCurrencyPair(): org.knowm.xchange.currency.CurrencyPair {
    return org.knowm.xchange.currency.CurrencyPair(Currency(this.base), Currency(this.quote))
}


fun Candle.toBar(timeFrame: TimeFrame): Bar {
    return BaseBar(
        Duration.between(openTime, openTime.plus(timeFrame.duration)),
        openTime.atZone(ZoneId.of("UTC")),
        open,
        high,
        low,
        close,
        volume,
        0.0,
        0,
        DecimalNum::valueOf
    )
}

fun List<Candle>.toBarSeries(timeFrame: TimeFrame): BarSeries {
    val series = BaseBarSeriesBuilder().build()
    this.distinctBy { it.openTime }.sortedBy { it.openTime }.forEach { series.addBar(it.toBar(timeFrame)) }
    return series
}
