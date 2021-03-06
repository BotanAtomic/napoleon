package esgi.napoleon.variables.primitive

import esgi.napoleon.utils.doubleEquals
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.primitive.api.NumberVariable
import org.ta4j.core.num.Num
import kotlin.math.floor
import kotlin.math.pow

class FloatVariable : Variable<Double>, NumberVariable {

    constructor(initialValue: String) : super(initialValue.toDouble())

    constructor(initialValue: Double) : super(initialValue)

    constructor(initialValue: Num) : super(initialValue.doubleValue())

    override fun type() = "float"

    override fun add(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> FloatVariable(value + by.value)
            is FloatVariable -> FloatVariable(value + by.value)
            is StringVariable -> StringVariable(valueToString() + by.value)
            else -> super.add(by)
        }
    }

    override fun subtract(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> FloatVariable(value - by.value)
            is FloatVariable -> FloatVariable(value - by.value)
            else -> super.subtract(by)
        }
    }

    override fun multiply(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> FloatVariable(value * by.value)
            is FloatVariable -> FloatVariable(value * by.value)
            else -> super.multiply(by)
        }
    }

    override fun power(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> FloatVariable(value.pow(by.value.toDouble()))
            is FloatVariable -> FloatVariable(value.pow(by.value))
            else -> super.power(by)
        }
    }


    override fun divide(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> FloatVariable(value / by.value)
            is FloatVariable -> FloatVariable(value / by.value)
            else -> super.divide(by)
        }
    }


    override fun floorDivide(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> FloatVariable(value / by.value)
            is FloatVariable -> FloatVariable(floor(value / by.value))
            else -> super.floorDivide(by)
        }
    }

    override fun modulo(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> FloatVariable(value % by.value)
            is FloatVariable -> FloatVariable(value % by.value)
            else -> super.modulo(by)
        }
    }


    override fun not(): BooleanVariable {
        return BooleanVariable(value == 0.0)
    }

    override fun eq(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is IntegerVariable -> BooleanVariable(value == variable.value.toDouble())
            is FloatVariable -> BooleanVariable(value.doubleEquals(variable.value))
            else -> super.eq(variable)
        }
    }

    override fun gt(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is IntegerVariable -> BooleanVariable(value > variable.value)
            is FloatVariable -> BooleanVariable(value > variable.value)
            else -> super.gt(variable)
        }
    }

    override fun gte(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is IntegerVariable -> BooleanVariable(value >= variable.value)
            is FloatVariable -> BooleanVariable(value >= variable.value)
            else -> super.gte(variable)
        }
    }

    override fun lt(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is IntegerVariable -> BooleanVariable(value < variable.value)
            is FloatVariable -> BooleanVariable(value < variable.value)
            else -> super.lt(variable)
        }
    }

    override fun lte(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is IntegerVariable -> BooleanVariable(value <= variable.value)
            is FloatVariable -> BooleanVariable(value <= variable.value)
            else -> super.lte(variable)
        }
    }

    override fun toBoolean(): BooleanVariable {
        return BooleanVariable(value == 0.0)
    }

    override fun toInt() = value.toInt()

    override fun toLong() = value.toLong()

    override fun toFloat() = value.toFloat()

    override fun toDouble() = value

    override fun isSerializable() = true

    override fun compareTo(other: Variable<*>): Int {
        return when(other) {
            is IntegerVariable -> value.compareTo(other.value)
            is FloatVariable -> value.compareTo(other.value)
            is StringVariable -> value.toString().compareTo(other.value)
            else -> 0
        }
    }
}
