package io.deepn.script.variables.primitive

import io.deepn.script.utils.doubleEquals
import io.deepn.script.variables.Variable
import io.deepn.script.variables.primitive.api.NumberVariable
import kotlin.math.floor
import kotlin.math.pow

class IntegerVariable : Variable<Long>, NumberVariable {

    constructor(initialValue: Long) : super(initialValue)

    constructor(initialValue: Int) : super(initialValue.toLong())

    constructor(initialValue: String) : super(initialValue.toLong())

    override fun type() = "integer"

    override fun add(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> IntegerVariable(value + by.value)
            is FloatVariable -> FloatVariable(value + by.value)
            is StringVariable -> StringVariable(valueToString() + by.value)
            else -> super.add(by)
        }
    }

    override fun subtract(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> IntegerVariable(value - by.value)
            is FloatVariable -> FloatVariable(value - by.value)
            else -> super.subtract(by)
        }
    }

    override fun multiply(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> IntegerVariable(value * by.value)
            is FloatVariable -> FloatVariable(value * by.value)
            else -> super.multiply(by)
        }
    }

    override fun power(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> {
                return if (by.value >= 0)
                    IntegerVariable((value.toDouble().pow(by.value.toDouble())).toLong())
                else
                    FloatVariable(value.toDouble().pow(by.value.toDouble()))
            }
            is FloatVariable -> FloatVariable(value.toDouble().pow(by.value))
            else -> super.power(by)
        }
    }


    override fun divide(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> FloatVariable(value.toDouble() / by.value)
            is FloatVariable -> FloatVariable(value / by.value)
            else -> super.divide(by)
        }
    }


    override fun floorDivide(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> IntegerVariable(value / by.value)
            is FloatVariable -> IntegerVariable(floor(value / by.value).toLong())
            else -> super.floorDivide(by)
        }
    }

    override fun modulo(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> IntegerVariable(value % by.value)
            is FloatVariable -> FloatVariable(value % by.value)
            else -> super.modulo(by)
        }
    }

    override fun bitwiseLeftShift(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> IntegerVariable(value.shl(by.value.toInt()))
            else -> super.bitwiseLeftShift(by)
        }
    }

    override fun bitwiseRightShift(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> IntegerVariable(value.shr(by.value.toInt()))
            else -> super.bitwiseRightShift(by)
        }
    }

    override fun bitwiseAnd(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> IntegerVariable(value.and(by.value))
            else -> super.bitwiseAnd(by)
        }
    }

    override fun bitwiseOr(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> IntegerVariable(value.or(by.value))
            else -> super.bitwiseOr(by)
        }
    }

    override fun bitwiseXor(by: Variable<*>): Variable<*> {
        return when (by) {
            is IntegerVariable -> IntegerVariable(value.xor(by.value))
            else -> super.bitwiseXor(by)
        }
    }

    override fun bitwiseInv(): Variable<*> {
        return IntegerVariable(value.inv())
    }

    override fun not(): BooleanVariable {
        return BooleanVariable(value == 0L)
    }

    override fun eq(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is IntegerVariable -> BooleanVariable(value == variable.value)
            is FloatVariable -> BooleanVariable(value.toDouble().doubleEquals(variable.value))
            else -> super.eq(variable)
        }
    }

    override fun gt(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is IntegerVariable -> BooleanVariable(value > variable.value)
            is FloatVariable -> BooleanVariable(value.toDouble() > variable.value)
            else -> super.gt(variable)
        }
    }

    override fun gte(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is IntegerVariable -> BooleanVariable(value >= variable.value)
            is FloatVariable -> BooleanVariable(value.toDouble() >= variable.value)
            else -> super.gte(variable)
        }
    }

    override fun lt(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is IntegerVariable -> BooleanVariable(value < variable.value)
            is FloatVariable -> BooleanVariable(value.toDouble() < variable.value)
            else -> super.lt(variable)
        }
    }

    override fun lte(variable: Variable<*>): BooleanVariable {
        return when (variable) {
            is IntegerVariable -> BooleanVariable(value <= variable.value)
            is FloatVariable -> BooleanVariable(value.toDouble() <= variable.value)
            else -> super.lte(variable)
        }
    }

    override fun toBoolean(): BooleanVariable {
        return BooleanVariable(value != 0L)
    }

    override fun toInt() = value.toInt()

    override fun toLong() = value

    override fun toFloat() = value.toFloat()

    override fun toDouble() = value.toDouble()
}
