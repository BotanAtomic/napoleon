package io.deepn.script.variables.primitive

import io.deepn.script.error.KeyError
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable


class ObjectVariable : Variable<MutableMap<String, Variable<*>>>(HashMap()) {

    override fun type() = "object"

    override fun getIndex(position: Variable<*>): Variable<*> {
        val key = position.valueToString()
        if (!value.containsKey(key))
            throw KeyError("no key '$key'")
        return value.getOrDefault(key, Null)
    }

    override fun setIndex(position: Variable<*>, variable: Variable<*>): Variable<*> {
        value[position.valueToString()] = variable
        return variable
    }

    override fun length(): IntegerVariable {
        return IntegerVariable(value.size)
    }
}
