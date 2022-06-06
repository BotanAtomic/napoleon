package esgi.napoleon.exchange.api.impl

import esgi.napoleon.exchange.api.ExchangeApi
import esgi.napoleon.exchange.api.entity.Candle
import esgi.napoleon.exchange.api.entity.CurrencyPair
import esgi.napoleon.exchange.api.entity.ExchangeCredentials
import esgi.napoleon.exchange.api.entity.TimeFrame
import esgi.napoleon.exchange.api.toUnifiedCurrencyPair
import org.knowm.xchange.binance.BinanceExchange
import org.knowm.xchange.binance.dto.marketdata.BinanceKline
import org.knowm.xchange.binance.dto.marketdata.KlineInterval
import org.knowm.xchange.binance.service.BinanceMarketDataService
import java.time.Instant
import java.time.ZoneId

class BinanceExchangeImpl(credentials: ExchangeCredentials?, sandbox: Boolean, initialize: Boolean) :
    ExchangeApi<BinanceExchange>(credentials, BinanceExchange::class.java, sandbox, initialize) {

    override val maximumCandleLimitPerRequest: Int = 1000
    override val zoneId: ZoneId by lazy { ZoneId.of((client.marketDataService as BinanceMarketDataService).exchangeInfo.timezone) }

    override fun getKlines(
        pair: CurrencyPair,
        interval: TimeFrame,
        startTime: Instant?,
        endTime: Instant?,
    ): List<Candle> =
        (client.marketDataService as BinanceMarketDataService).klines(
            pair.toUnifiedCurrencyPair(),
            interval.toKline(),
            maximumCandleLimitPerRequest,
            startTime?.toEpochMilli(),
            endTime?.toEpochMilli()
        ).toCandle()

}

private fun TimeFrame.toKline(): KlineInterval =
    KlineInterval.values().firstOrNull { it.millis == this.duration.toMillis() }
        ?: throw IllegalStateException("Unknown interval $this")

private fun List<BinanceKline>.toCandle() = map {
    Candle().apply {
        openTime = Instant.ofEpochMilli(it.openTime)
        open = it.openPrice.toDouble()
        high = it.highPrice.toDouble()
        low = it.lowPrice.toDouble()
        close = it.closePrice.toDouble()
        volume = it.volume.toDouble()
    }
}.sortedBy { it.openTime }
