package esgi.napoleon.exchange.api.entity

typealias Currency = String


data class CurrencyPair(val base: Currency, val quote: Currency) {

    /**
     * OKEX:     https://www.okex.com/docs/en
     * BINANCE:  https://binance-docs.github.io/apidocs/spot/en/
     * BITFINEX: https://docs.bitfinex.com/docs/introduction
     * FTX:      https://docs.ftx.com/#overview
     * COINBASE: https://developers.coinbase.com/
     * KUCOIN:   https://docs.kucoin.com/
     * KRAKEN:   https://docs.kraken.com/rest/
     * BYBIT:    https://bybit-exchange.github.io/docs/inverse/
     */
    fun format()= "$base/$quote"
}
