package io.deepn.flow.variables.primitive

import io.deepn.flow.scope.VariableMap
import io.deepn.flow.stdlib.JsonLibrary.stringify
import io.deepn.flow.variables.Null
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.memory.MemoryAddressVariable

open class ObjectVariable(
    defaultValues: VariableMap? = null
) : Variable<VariableMap>(defaultValues ?: VariableMap()) {

    override fun type() = "object"

    override fun getIndex(position: Variable<*>): Variable<*> {
        val key = position.valueToString()
        if (!value.containsKey(key))
            return MemoryAddressVariable { value, _ -> setIndex(position, value) }
        return value.getOrDefault(key, Null)
    }

    override fun setIndex(position: Variable<*>, variable: Variable<*>): Variable<*> {
        value[position.valueToString()] = variable
        return variable
    }

    override fun deleteIndex(position: Variable<*>): Variable<*> {
        return value.remove(position.valueToString()) ?: Null
    }

    override fun length(): IntegerVariable {
        return IntegerVariable(value.size)
    }


    operator fun get(key: String): Variable<*> {
        return value.getOrDefault(key, Null)
    }

    operator fun set(key: String, value: Variable<*>) {
        super.value[key] = value
    }

    override fun valueToString() = this.stringify(BooleanVariable(true)).value

    override fun isSerializable() = true

}