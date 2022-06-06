package esgi.napoleon.exchange.api

import esgi.napoleon.exchange.api.entity.Exchange
import esgi.napoleon.exchange.api.entity.ExchangeCredentials
import esgi.napoleon.exchange.api.impl.BinanceExchangeImpl

fun Exchange.createExchangeApi(
    credentials: ExchangeCredentials? = null,
    initialize: Boolean = false,
    sandbox: Boolean = false
): ExchangeApi<out org.knowm.xchange.Exchange> =
    when (this) {
        Exchange.BINANCE -> BinanceExchangeImpl(credentials, initialize, sandbox)
        else -> throw IllegalArgumentException(this.name)
    }
