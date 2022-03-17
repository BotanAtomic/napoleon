package io.deepn.flow.variables.ta

import io.deepn.flow.error.IndexError
import io.deepn.flow.error.TypeError
import io.deepn.flow.stdlib.libs.ArithmeticIndicator
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.primitive.BooleanVariable
import io.deepn.flow.variables.primitive.FloatVariable
import io.deepn.flow.variables.primitive.IntegerVariable
import org.ta4j.core.BarSeries
import org.ta4j.core.Indicator
import org.ta4j.core.num.Num

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
            val index = series.endIndex - position.toInt()
            if (index < 0 || index > series.endIndex)
                throw IndexError("indicator index out of range: index=${index}, size=${series.barCount}")

            return FloatVariable(value.getValue(series.endIndex - position.toInt()))
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

    override fun valueToString() = currentValue().valueToString()

    override fun length() = IntegerVariable(series.barCount)

}
