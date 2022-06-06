package esgi.napoleon.exchange.api.entity

import java.time.Duration

enum class TimeFrame(val code: String, val duration: Duration) {
    m1("1m", Duration.ofMinutes(1)),
    m3("3m", Duration.ofMinutes(3)),
    m5("5m", Duration.ofMinutes(5)),
    m15("15m", Duration.ofMinutes(15)),
    m30("30m", Duration.ofMinutes(30)),

    h1("1h", Duration.ofHours(1)),
    h2("2h", Duration.ofHours(2)),
    h4("4h", Duration.ofHours(4)),
    h6("6h", Duration.ofHours(6)),
    h8("8h", Duration.ofHours(8)),

    d1("1d", Duration.ofDays(1)),
    w1("1w", Duration.ofDays(7));

    companion object {
        fun get(code: String): TimeFrame? = values().firstOrNull { it.code == code }
    }

}

