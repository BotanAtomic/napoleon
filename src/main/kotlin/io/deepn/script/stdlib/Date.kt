package io.deepn.script.stdlib

import io.deepn.script.utils.runOrValueError
import io.deepn.script.variables.Variable
import io.deepn.script.variables.primitive.BooleanVariable
import io.deepn.script.variables.primitive.IntegerVariable
import io.deepn.script.variables.primitive.StringVariable
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class DateTimeVariable(date: LocalDateTime) : Variable<LocalDateTime>(date) {

    override fun type() = "date_time"

    override fun subtract(by: Variable<*>): Variable<*> {
        return when (by) {
            is DateTimeVariable -> DurationVariable(Duration.between(this.value, by.value))
            is TimeVariable -> DurationVariable(Duration.between(this.value, by.value))
            else -> super.subtract(by)
        }
    }

}

class DateVariable(date: LocalDate) : Variable<LocalDate>(date) {

    override fun type() = "date"

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

}

class DurationVariable(duration: Duration) : Variable<Duration>(duration) {

    override fun type() = "duration"

}

@Package("date")
object Date {

    fun currentDateTime() = DateTimeVariable(LocalDateTime.now())

    fun currentDate() = DateVariable(LocalDate.now())

    fun currentTime() = TimeVariable(LocalTime.now())

    fun DateTimeVariable.format(format: StringVariable): Variable<*> {
        return runOrValueError { StringVariable(this.value.format(DateTimeFormatter.ofPattern(format.value))) }
    }

    fun DateVariable.format(format: StringVariable): Variable<*> {
        return runOrValueError { StringVariable(this.value.format(DateTimeFormatter.ofPattern(format.value))) }
    }

    fun TimeVariable.format(format: StringVariable): Variable<*> {
        return runOrValueError { StringVariable(this.value.format(DateTimeFormatter.ofPattern(format.value))) }
    }

    fun parseDateTime(date: StringVariable, format: StringVariable) = runOrValueError {
        DateTimeVariable(
            LocalDateTime.parse(
                date.value,
                DateTimeFormatter.ofPattern(format.value)
            )
        )
    }

    fun parseDate(date: StringVariable, format: StringVariable) = runOrValueError {
        DateVariable(
            LocalDate.parse(
                date.value,
                DateTimeFormatter.ofPattern(format.value)
            )
        )
    }

    fun parseTime(date: StringVariable, format: StringVariable) = runOrValueError {
        TimeVariable(
            LocalTime.parse(
                date.value,
                DateTimeFormatter.ofPattern(format.value)
            )
        )
    }

    /** DateTime **/
    fun DateTimeVariable.year() = IntegerVariable(this.value.year)

    fun DateTimeVariable.month() = IntegerVariable(this.value.monthValue)

    fun DateTimeVariable.dayOfMonth() = IntegerVariable(this.value.dayOfMonth)

    fun DateTimeVariable.dayOfYear() = IntegerVariable(this.value.dayOfYear)

    fun DateTimeVariable.dayOfWeek() = IntegerVariable(this.value.dayOfWeek.value)

    fun DateTimeVariable.hour() = IntegerVariable(this.value.hour)

    fun DateTimeVariable.minute() = IntegerVariable(this.value.minute)

    fun DateTimeVariable.second() = IntegerVariable(this.value.second)

    fun DateTimeVariable.plusYears(years: IntegerVariable) = runOrValueError {
        DateTimeVariable(this.value.plusYears(years.value))
    }

    fun DateTimeVariable.minusYears(years: IntegerVariable) = runOrValueError {
        DateTimeVariable(this.value.minusYears(years.value))
    }


    fun DateTimeVariable.plusMonths(months: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.plusMonths(months.value)) }

    fun DateTimeVariable.minusMonths(months: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.minusMonths(months.value)) }

    fun DateTimeVariable.plusWeeks(weeks: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.plusWeeks(weeks.value)) }

    fun DateTimeVariable.minusWeeks(weeks: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.minusWeeks(weeks.value)) }

    fun DateTimeVariable.plusDays(days: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.plusDays(days.value)) }

    fun DateTimeVariable.minusDays(days: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.minusDays(days.value)) }

    fun DateTimeVariable.plusHours(hours: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.plusHours(hours.value)) }

    fun DateTimeVariable.minusHours(hours: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.minusHours(hours.value)) }

    fun DateTimeVariable.plusMinutes(minutes: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.plusMinutes(minutes.value)) }

    fun DateTimeVariable.minusMinutes(minutes: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.minusMinutes(minutes.value)) }

    fun DateTimeVariable.plusSeconds(seconds: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.plusSeconds(seconds.value)) }

    fun DateTimeVariable.minusSeconds(seconds: IntegerVariable) =
        runOrValueError { DateTimeVariable(this.value.minusSeconds(seconds.value)) }

    fun DateTimeVariable.isAfter(date: DateTimeVariable) = BooleanVariable(this.value.isAfter(date.value))
    fun DateTimeVariable.isBefore(date: DateTimeVariable) = BooleanVariable(this.value.isBefore(date.value))
    fun DateTimeVariable.isEqual(date: DateTimeVariable) = BooleanVariable(this.value.isEqual(date.value))

    /** Date **/
    fun DateVariable.year() = IntegerVariable(this.value.year)

    fun DateVariable.month() = IntegerVariable(this.value.monthValue)

    fun DateVariable.dayOfMonth() = IntegerVariable(this.value.dayOfMonth)

    fun DateVariable.dayOfYear() = IntegerVariable(this.value.dayOfYear)

    fun DateVariable.dayOfWeek() = IntegerVariable(this.value.dayOfWeek.value)

    fun DateVariable.plusYears(years: IntegerVariable) =
        runOrValueError { DateVariable(this.value.plusYears(years.value)) }

    fun DateVariable.minusYears(years: IntegerVariable) =
        runOrValueError { DateVariable(this.value.minusYears(years.value)) }

    fun DateVariable.plusMonths(months: IntegerVariable) =
        runOrValueError { DateVariable(this.value.plusMonths(months.value)) }

    fun DateVariable.minusMonths(months: IntegerVariable) =
        runOrValueError { DateVariable(this.value.minusMonths(months.value)) }

    fun DateVariable.plusWeeks(weeks: IntegerVariable) =
        runOrValueError { DateVariable(this.value.plusWeeks(weeks.value)) }

    fun DateVariable.minusWeeks(weeks: IntegerVariable) =
        runOrValueError { DateVariable(this.value.minusWeeks(weeks.value)) }

    fun DateVariable.plusDays(days: IntegerVariable) =
        runOrValueError { DateVariable(this.value.plusDays(days.value)) }

    fun DateVariable.minusDays(days: IntegerVariable) =
        runOrValueError { DateVariable(this.value.minusDays(days.value)) }

    fun DateVariable.isAfter(date: DateVariable) = BooleanVariable(this.value.isAfter(date.value))
    fun DateVariable.isBefore(date: DateVariable) = BooleanVariable(this.value.isBefore(date.value))
    fun DateVariable.isEqual(date: DateVariable) = BooleanVariable(this.value.isEqual(date.value))

    /** Time **/
    fun TimeVariable.hour() = IntegerVariable(this.value.hour)

    fun TimeVariable.minute() = IntegerVariable(this.value.minute)

    fun TimeVariable.second() = IntegerVariable(this.value.second)

    fun TimeVariable.plusHours(hours: IntegerVariable) = TimeVariable(this.value.plusHours(hours.value))
    fun TimeVariable.minusHours(hours: IntegerVariable) = TimeVariable(this.value.minusHours(hours.value))

    fun TimeVariable.plusMinutes(minutes: IntegerVariable) = TimeVariable(this.value.plusMinutes(minutes.value))
    fun TimeVariable.minusMinutes(minutes: IntegerVariable) = TimeVariable(this.value.minusMinutes(minutes.value))

    fun TimeVariable.plusSeconds(seconds: IntegerVariable) = TimeVariable(this.value.plusSeconds(seconds.value))
    fun TimeVariable.minusSeconds(seconds: IntegerVariable) = TimeVariable(this.value.minusSeconds(seconds.value))

    fun TimeVariable.isAfter(time: TimeVariable) = BooleanVariable(this.value.isAfter(time.value))
    fun TimeVariable.isBefore(time: TimeVariable) = BooleanVariable(this.value.isBefore(time.value))

    /** Duration **/

    fun DurationVariable.isZero() = BooleanVariable(this.value.isZero)

    fun DurationVariable.isNegative() = BooleanVariable(this.value.isNegative)

    fun DurationVariable.negated() = DurationVariable(this.value.negated())

    fun DurationVariable.abs() = DurationVariable(this.value.abs())

    fun DurationVariable.toDays() = IntegerVariable(this.value.toDays())
    fun DurationVariable.toHours() = IntegerVariable(this.value.toHours())
    fun DurationVariable.toMinutes() = IntegerVariable(this.value.toMinutes())
    fun DurationVariable.toSeconds() = IntegerVariable(this.value.toSeconds())
    fun DurationVariable.toMillis() = IntegerVariable(this.value.toMillis())

    fun DurationVariable.toDaysPart() = IntegerVariable(this.value.toDaysPart())
    fun DurationVariable.toHoursPart() = IntegerVariable(this.value.toHoursPart())
    fun DurationVariable.toMinutesPart() = IntegerVariable(this.value.toMinutesPart())
    fun DurationVariable.toSecondsPart() = IntegerVariable(this.value.toSecondsPart())
    fun DurationVariable.toMillisPart() = IntegerVariable(this.value.toMillisPart())

    fun DurationVariable.minusDays(days: IntegerVariable) = DurationVariable(this.value.minusDays(days.value))
    fun DurationVariable.minusHours(hours: IntegerVariable) = DurationVariable(this.value.minusHours(hours.value))
    fun DurationVariable.minusMinutes(minutes: IntegerVariable) =
        DurationVariable(this.value.minusMinutes(minutes.value))

    fun DurationVariable.minusSeconds(seconds: IntegerVariable) =
        DurationVariable(this.value.minusSeconds(seconds.value))

    fun DurationVariable.minusMillis(millis: IntegerVariable) = DurationVariable(this.value.minusMillis(millis.value))

    fun DurationVariable.plusDays(days: IntegerVariable) = DurationVariable(this.value.plusDays(days.value))
    fun DurationVariable.plusHours(hours: IntegerVariable) = DurationVariable(this.value.plusHours(hours.value))
    fun DurationVariable.plusMinutes(minutes: IntegerVariable) = DurationVariable(this.value.plusMinutes(minutes.value))
    fun DurationVariable.plusSeconds(seconds: IntegerVariable) = DurationVariable(this.value.plusSeconds(seconds.value))
    fun DurationVariable.plusMillis(millis: IntegerVariable) = DurationVariable(this.value.plusMillis(millis.value))

}
