package esgi.napoleon.strategy

import esgi.napoleon.database.InfluxDatabase
import esgi.napoleon.error.NameError
import esgi.napoleon.error.ValueError
import esgi.napoleon.exchange.api.ExchangeApi
import esgi.napoleon.exchange.api.createExchangeApi
import esgi.napoleon.exchange.api.entity.CurrencyPair
import esgi.napoleon.exchange.api.entity.Exchange
import esgi.napoleon.exchange.api.entity.TimeFrame
import esgi.napoleon.exchange.api.toBarSeries
import esgi.napoleon.logger.Logger
import esgi.napoleon.variables.Null
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.date.DateTimeVariable
import esgi.napoleon.variables.date.DateVariable
import esgi.napoleon.variables.primitive.IntegerVariable
import esgi.napoleon.variables.primitive.StringVariable
import esgi.napoleon.variables.primitive.api.NumberVariable
import esgi.napoleon.variables.ta.BarSeriesVariable
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import kotlin.math.ceil

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class StrategyFunction

typealias StrategyFunctionArguments = Map<String, Variable<*>>

abstract class StrategyHandler {

    protected val functions = HashMap<String, (StrategyFunctionArguments) -> Variable<*>>()

    fun call(id: String, parameters: StrategyFunctionArguments): Variable<*> {
        if (functions.containsKey(id.lowercase()).not())
            throw NameError("there is no strategy function named '$id'")
        val strategyFunction = functions[id.lowercase()]

        if (strategyFunction != null)
            return strategyFunction(parameters)

        return Null
    }

}

class EmptyStrategyHandler : StrategyHandler() {

    init {
        functions["buy"] = {
            Null
        }

        functions["sell"] = {
            Null
        }
    }

}

class DefaultStrategyHandler(logger: Logger) : StrategyHandler() {


    private val exchanges: Map<String, ExchangeApi<out org.knowm.xchange.Exchange>> = mapOf(
        "binance" to Exchange.BINANCE.createExchangeApi(initialize = true)
    )

    fun dateToInstant(date: Variable<*>, key: String): Instant {
        return when (date) {
            is NumberVariable -> Instant.ofEpochMilli(date.toLong())
            is StringVariable -> Instant.parse(date.value)
            is DateVariable -> date.value.atStartOfDay().toInstant(ZoneOffset.UTC)
            is DateTimeVariable -> date.value.toInstant(ZoneOffset.UTC)
            is Null -> Instant.now()
            else -> throw IllegalArgumentException("$key has an invalid date format, accepted values are [Timestamp, ISO String, Date, DateTime]")
        }
    }

    init {
        functions["ohlcv"] = {
            val startTime = it.getOrDefault("from", Null)
            val endTime = it.getOrDefault("to", Null)
            val interval = it.getOrDefault("tf", Null)
            val exchange = it.getOrDefault("exchange", StringVariable("binance"))
            val base = it.getOrDefault("base", Null)
            val quote = it.getOrDefault("quote", Null)

            val from: Instant = dateToInstant(startTime, "from")
            val to: Instant = dateToInstant(endTime, "to")

            if (base == Null || base !is StringVariable) throw NameError("base is a required parameter")
            if (quote == Null || quote !is StringVariable) throw NameError("quote is a required parameter")

            if (interval !is StringVariable) throw NameError("timeframe must be a string")
            if (exchange !is StringVariable) throw NameError("exchange must be a string")

            val timeFrame = TimeFrame.get(interval.value) ?: throw NameError(
                "timeframe must be a valid timeframe, accepted values: ${
                    TimeFrame.values().map(TimeFrame::code)
                }"
            )

            if(from > to) throw ValueError("from must be before to")

            val exchangeInstance = exchanges[exchange.valueToString()]
                ?: throw ValueError("exchange ${exchange.valueToString()} is not supported yet")

            val currencyPair = CurrencyPair(base.value, quote.value)

            var candles = InfluxDatabase.getCandles(
                exchange.valueToString().uppercase(), currencyPair.format(), timeFrame, from, to
            )

            val waitedCandlesNumber = Duration.between(from, to).dividedBy(timeFrame.duration) + 1

            if(waitedCandlesNumber != candles.size.toLong()) {
                candles = exchangeInstance.getCandles(
                    currencyPair,
                    timeFrame,
                    from,
                    to
                )
                InfluxDatabase.insert( exchange.valueToString().uppercase(), currencyPair.format(), timeFrame, candles)
                logger.log("Successfully downloaded ${candles.size} candles from exchange")
            } else {
                logger.log("Successfully downloaded ${candles.size} candles from cache")
            }

            BarSeriesVariable(candles.toBarSeries(timeFrame))
        }

        functions["hold"] = { IntegerVariable(0) }
        functions["buy"] = { IntegerVariable(1) }
        functions["sell"] = { IntegerVariable(2) }

    }

}
