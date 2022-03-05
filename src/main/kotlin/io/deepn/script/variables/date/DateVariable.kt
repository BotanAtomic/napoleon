package io.deepn.script.variables.date

import io.deepn.script.variables.Variable
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class DateTimeVariable(date: LocalDateTime) : Variable<LocalDateTime>(date) {

    override fun type() = "date_time"

    override fun subtract(by: Variable<*>): Variable<*> {
        return when (by) {
            is DateTimeVariable -> DurationVariable(Duration.between(this.value, by.value))
            is TimeVariable -> DurationVariable(Duration.between(this.value, by.value))
            else -> super.subtract(by)
        }
    }

    override fun isSerializable() = true

}

class DateVariable(date: LocalDate) : Variable<LocalDate>(date) {

    override fun type() = "date"

    override fun isSerializable() = true

}

class TimeVariable(time: LocalTime) : Variable<LocalTime>(time) {

    override fun type() = "time"

    override fun subtract(by: Variable<*>): Variable<*> {
        return when (by) {
            is DateTimeVariable -> DurationVariable(Duration.between(this.value, by.value))
            is TimeVariable -> DurationVariable(Duration.between(this.value, by.value))
            else -> super.subtract(by)
        }
    }

    override fun isSerializable() = true

}

class DurationVariable(duration: Duration) : Variable<Duration>(duration) {

    override fun type() = "duration"

    override fun isSerializable() = true
}
