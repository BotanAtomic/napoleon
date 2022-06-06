package esgi.napoleon.exchange.api.entity

import com.influxdb.annotations.Column
import java.time.Instant

class Candle {
    @Column var symbol: String = ""
    @Column(name = "_time") var openTime: Instant = Instant.MIN
    @Column
    var open: Double = -1.0
    @Column var high: Double = -1.0
    @Column var low: Double = -1.0
    @Column var close: Double = -1.0
    @Column var volume: Double = -1.0

    override fun toString(): String {
        return "[openTime: $openTime, open: $open, high: $high, low: $low, close: $close, volume: $volume]"
    }

    override fun equals(other: Any?): Boolean = if (other is Candle) {
        other.open == this.open &&
                other.high == this.high &&
                other.low == this.low &&
                other.close == this.close &&
                other.volume == this.volume
    } else false

    override fun hashCode(): Int {
        var result = openTime.hashCode()
        result = 31 * result + open.hashCode()
        result = 31 * result + high.hashCode()
        result = 31 * result + low.hashCode()
        result = 31 * result + close.hashCode()
        result = 31 * result + volume.hashCode()
        return result

    }
}
