package esgi.napoleon.database

import com.influxdb.client.InfluxDBClientFactory
import com.influxdb.client.InfluxDBClientOptions
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import com.influxdb.query.dsl.Flux
import com.influxdb.query.dsl.functions.RangeFlux
import com.influxdb.query.dsl.functions.restriction.Restrictions
import esgi.napoleon.exchange.api.entity.Candle
import esgi.napoleon.exchange.api.entity.Exchange
import esgi.napoleon.exchange.api.entity.TimeFrame
import java.time.Instant

fun Flux.rangeInclusive(start: Instant, stop: Instant): RangeFlux = range(start, stop.plusNanos(5))
private const val TAG_SYMBOL = "symbol"
private const val TAG_TIMEFRAME = "timeframe"

object InfluxDatabase {

    val bucketName = System.getenv().getOrDefault("INFLUX_BUCKET", "Napoleon")

    val client = InfluxDBClientFactory.create(
        InfluxDBClientOptions.builder()
            .url(System.getenv().getOrDefault("INFLUX_URL", "http://localhost:7086"))
            .authenticateToken(System.getenv().getOrDefault("INFLUX_TOKEN", "2voPfcBLl8mMfgz0-mBNatIXfrfyCpbxla5qipvHsZBisjpAiwShYoHRlRMpTyYH_IuKgYUK4x2qJfILa0441g==\n").toCharArray())
            .bucket(bucketName)
            .org(System.getenv().getOrDefault("INFLUX_ORG", "ESGI"))
            .build()
    )

    fun getCandles(exchange: String, symbol: String, timeFrame: TimeFrame, startTime: Instant, endTime: Instant): List<Candle> =
        kotlin.runCatching { client.queryApi.query(
            Flux.from(bucketName)
                .rangeInclusive(startTime, endTime)
                .filter(
                    Restrictions.and(
                    Restrictions.measurement().equal(exchange),
                    Restrictions.tag(TAG_SYMBOL).equal(symbol),
                    Restrictions.tag(TAG_TIMEFRAME).equal(timeFrame.name),
                ))
                .pivot()
                .withRowKey(listOf("_time"))
                .withColumnKey(listOf("_field"))
                .withValueColumn("_value").toString(),
            Candle::class.java
        ) }.getOrElse { listOf() }

    fun insert(exchange: String, symbol: String, timeFrame: TimeFrame, candles: List<Candle>) =
        kotlin.runCatching { client.writeApiBlocking.writePoints(candles.toPoint(exchange, symbol, timeFrame.name)) }

    private fun List<Candle>.toPoint(exchange: String, symbol: String, timeFrame: String) = map { candle ->
        Point(exchange).addTags(mapOf(TAG_SYMBOL to symbol, TAG_TIMEFRAME to timeFrame))
            .time(candle.openTime, WritePrecision.MS)
            .addFields(mapOf(
                "open" to candle.open,
                "high" to candle.high,
                "low" to candle.low,
                "close" to candle.close,
                "volume" to candle.volume,
            ))
    }


}
