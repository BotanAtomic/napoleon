package esgi.napoleon.variables.ta

import esgi.napoleon.error.IndexError
import esgi.napoleon.error.TypeError
import esgi.napoleon.stdlib.libs.ArithmeticIndicator
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.primitive.BooleanVariable
import esgi.napoleon.variables.primitive.FloatVariable
import esgi.napoleon.variables.primitive.IntegerVariable
import org.http4k.format.Gson.asJsonObject
import org.ta4j.core.BarSeries
import org.ta4j.core.Indicator
import org.ta4j.core.num.Num
import java.util.stream.Collectors

typealias NumberIndicator = Indicator<Num>

fun IndicatorVariable.operationOr(
    by: Variable<*>,
    operation: (Num, Num) -> Num,
    orElse: () -> Variable<*>
): Variable<*> {
    return when (by) {
        is IntegerVariable -> IndicatorVariable(ArithmeticIndicator(value, by.value, operation))
        is FloatVariable -> IndicatorVariable(ArithmeticIndicator(value, by.value, operation))
        is IndicatorVariable -> IndicatorVariable(ArithmeticIndicator(value, by.value, operation))
        else -> orElse()
    }
}

class IndicatorVariable(indicator: NumberIndicator) : Variable<NumberIndicator>(indicator) {

    private val series: BarSeries = value.barSeries

    private fun currentValue(): FloatVariable = FloatVariable(value.getValue(series.endIndex))

    override fun add(by: Variable<*>): Variable<*> {
        return operationOr(by, { a, b -> a.plus(b) }, { super.add(by) })
    }

    override fun subtract(by: Variable<*>): Variable<*> {
        return operationOr(by, { a, b -> a.minus(b) }, { super.subtract(by) })
    }

    override fun multiply(by: Variable<*>): Variable<*> {
        return operationOr(by, { a, b -> a.multipliedBy(b) }, { super.multiply(by) })
    }

    override fun power(by: Variable<*>): Variable<*> {
        return operationOr(by, { a, b -> a.pow(b) }, { super.power(by) })
    }

    override fun divide(by: Variable<*>): Variable<*> {
        return operationOr(by, { a, b -> a.dividedBy(b) }, { super.divide(by) })
    }


    override fun floorDivide(by: Variable<*>): Variable<*> {
        return operationOr(by, { a, b -> a.dividedBy(b).floor() }, { super.floorDivide(by) })
    }

    override fun modulo(by: Variable<*>): Variable<*> {
        return operationOr(by, { a, b -> a.remainder(b) }, { super.modulo(by) })
    }

    override fun getIndex(position: Variable<*>): Variable<*> {
        if (position is IntegerVariable) {
            val index = position.toInt()
            if (index < 0 || index > series.endIndex)
                throw IndexError("indicator index out of range: index=${index}, size=${series.barCount}")

            return FloatVariable(value.getValue(position.toInt()))
        }
        throw TypeError("indicator index must be integer, not '${position.type()}'")
    }

    override fun gt(variable: Variable<*>) = currentValue().gt(variable)

    override fun gte(variable: Variable<*>) = currentValue().gte(variable)

    override fun lt(variable: Variable<*>) = currentValue().lt(variable)

    override fun lte(variable: Variable<*>) = currentValue().lte(variable)

    override fun eq(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is FloatVariable -> currentValue().eq(variable)
            is IndicatorVariable -> currentValue().eq(variable.currentValue())
            else -> super.eq(variable)
        }
    }

    override fun type() = "indicator"

    override fun valueToString(): String = value.stream().limit(20).map { String.format("%.2f", it.floatValue()) }
        .collect(Collectors.joining(",", "[", "]"))

    override fun length() = IntegerVariable(series.barCount)

}

class BooleanIndicatorVariable(indicator: Indicator<Boolean>) : Variable<Indicator<Boolean>>(indicator) {

    private val series: BarSeries = value.barSeries

    private fun currentValue(): BooleanVariable = BooleanVariable(value.getValue(series.endIndex))

    override fun getIndex(position: Variable<*>): Variable<*> {
        if (position is IntegerVariable) {
            val index = position.toInt()
            if (index < 0 || index > series.endIndex)
                throw IndexError("indicator index out of range: index=${index}, size=${series.barCount}")

            return BooleanVariable(value.getValue(position.toInt()))
        }
        throw TypeError("indicator index must be integer, not '${position.type()}'")
    }

    override fun gt(variable: Variable<*>) = currentValue().gt(variable)

    override fun gte(variable: Variable<*>) = currentValue().gte(variable)

    override fun lt(variable: Variable<*>) = currentValue().lt(variable)

    override fun lte(variable: Variable<*>) = currentValue().lte(variable)

    override fun eq(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is BooleanVariable -> currentValue().eq(variable)
            is BooleanIndicatorVariable -> currentValue().eq(variable.currentValue())
            else -> super.eq(variable)
        }
    }

    override fun type() = "indicator"

    override fun valueToString(): String = value.stream().limit(20).map { it.toString() }
        .collect(Collectors.joining(",", "[", "]"))

    override fun length() = IntegerVariable(series.barCount)

}
