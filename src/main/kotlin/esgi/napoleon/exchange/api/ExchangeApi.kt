package esgi.napoleon.exchange.api

import esgi.napoleon.exchange.api.entity.*
import esgi.napoleon.exchange.api.entity.Candle
import org.knowm.xchange.Exchange
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.ExchangeSpecification
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import kotlin.math.ceil

private fun Instant.min(second: Instant) = if (this.isAfter(second)) second else this

private fun chunkInstant(start: Instant, end: Instant, chunkDuration: Duration, limit: Int): List<Pair<Instant, Instant>> {
    val chunkSize = ceil(Duration.between(start, end).dividedBy(chunkDuration) / limit.toDouble()).toInt()
    return (0 until chunkSize).map { i ->
        Pair(
            start.plus(chunkDuration.multipliedBy(i.toLong() * limit)),
            end.min(start.plus(chunkDuration.multipliedBy((i.toLong() * limit) + limit)))
        )
    }
}

abstract class ExchangeApi<T : Exchange>(
    credentials: ExchangeCredentials?,
    exchangeClass: Class<T>,
    private var initialize: Boolean,
    sandbox: Boolean,
) {

    init { if(initialize) initialize() }

    open val client: Exchange = ExchangeFactory.INSTANCE.createExchange(ExchangeSpecification(exchangeClass).apply {
        isShouldLoadRemoteMetaData = false
        if (credentials != null) {
            apiKey = credentials.key
            secretKey = credentials.secret
        }
        if(credentials?.passphrase != null) setExchangeSpecificParametersItem("passphrase", credentials.passphrase)
        setExchangeSpecificParametersItem("Use_Sandbox", sandbox)
    })

    abstract val maximumCandleLimitPerRequest: Int
    open val zoneId: ZoneId = ZoneOffset.UTC
    val currencyPairs by lazy {
        initialize()
        client.exchangeSymbols.map { CurrencyPair(it.base.currencyCode, it.counter.currencyCode) }
    }

    fun initialize() {
        if(!initialize) client.remoteInit()
        initialize = true
    }

    protected abstract fun getKlines(pair: CurrencyPair,
                                     interval: TimeFrame,
                                     startTime: Instant? = null,
                                     endTime: Instant? = null): List<Candle>

    fun isCredentialsValid(): Boolean {
        return runCatching {
            client.accountService.accountInfo
            true
        }.getOrDefault(false)
    }

    fun getCandles(
        pair: CurrencyPair,
        timeFrame: TimeFrame,
        startTime: Instant? = null,
        endTime: Instant? = null
    ): List<Candle> =
        if (startTime == null || endTime == null) getKlines(pair, timeFrame, startTime, endTime)
        else chunkInstant(startTime, endTime, timeFrame.duration, maximumCandleLimitPerRequest)
                .map { getKlines(pair, timeFrame, it.first, it.second) }
                .flatten()


}
