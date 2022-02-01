package io.deepn.script.variables.primitive

import io.deepn.script.variables.Variable

class BooleanVariable(initialValue: Boolean) : Variable<Boolean>(initialValue) {

    override fun type() = "boolean"

    override fun not(): BooleanVariable {
        return BooleanVariable(!value)
    }

    override fun eq(variable: Variable<*>): BooleanVariable {
        if (variable is BooleanVariable)
            return BooleanVariable(value == variable.value)
        return super.eq(variable)
    }

    override fun and(variable: Variable<*>): BooleanVariable {
        if (variable is BooleanVariable)
            return BooleanVariable(value && variable.value)
        return super.and(variable)
    }

    override fun or(variable: Variable<*>): BooleanVariable {
        if (variable is BooleanVariable)
            return BooleanVariable(value || variable.value)
        return super.or(variable)
    }

    override fun toBoolean(): BooleanVariable {
        return this
    }
}
