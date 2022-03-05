package io.deepn.script.variables.primitive

import io.deepn.script.error.IndexError
import io.deepn.script.error.TypeError
import io.deepn.script.variables.Variable

class StringVariable(initialValue: String) : Variable<String>(initialValue) {

    override fun type() = "string"

    override fun add(by: Variable<*>): Variable<*> {
        return StringVariable(value + by.valueToString())
    }

    override fun eq(variable: Variable<*>): BooleanVariable {
        if (variable is StringVariable)
            return BooleanVariable(value == variable.value)
        return super.eq(variable)
    }

    override fun gt(variable: Variable<*>): BooleanVariable {
        if (variable is StringVariable)
            return BooleanVariable(value > variable.value)
        return super.gt(variable)
    }

    override fun gte(variable: Variable<*>): BooleanVariable {
        if (variable is StringVariable)
            return BooleanVariable(value >= variable.value)
        return super.gte(variable)
    }

    override fun lt(variable: Variable<*>): BooleanVariable {
        if (variable is StringVariable)
            return BooleanVariable(value < variable.value)
        return super.lt(variable)
    }

    override fun lte(variable: Variable<*>): BooleanVariable {
        if (variable is StringVariable)
            return BooleanVariable(value <= variable.value)
        return super.lte(variable)
    }

    override fun toBoolean(): BooleanVariable {
        return BooleanVariable(value.isNotEmpty())
    }

    override fun toIterator(): Iterator<Variable<*>> {
        val primaryIterator = value.iterator()
        return object : Iterator<Variable<*>> {
            override fun hasNext() = primaryIterator.hasNext()

            override fun next(): Variable<*> {
                return StringVariable(primaryIterator.next().toString())
            }
        }
    }

    override fun getIndex(position: Variable<*>): Variable<*> {
        if (position is IntegerVariable) {
            val index = position.value.toInt()
            if (index < 0 || index >= value.length)
                throw IndexError("string index out of range: index=${index}, size=${value.length}")

            return StringVariable(value[index].toString())
        }
        throw TypeError("string index must be integer, not '${position.type()}'")
    }

    override fun length(): IntegerVariable {
        return IntegerVariable(value.length)
    }

    override fun isSerializable() = true
}
