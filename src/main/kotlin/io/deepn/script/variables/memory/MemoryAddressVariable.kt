package io.deepn.script.variables.memory

import io.deepn.script.variables.Variable

class MemoryAddressVariable(private val onAssign: (Variable<*>, Any?) -> Unit) : Variable<Any>(Any()) {

    override fun type() = "null"

    fun assign(variable: Variable<*>, parameter: Any? = null) {
        onAssign(variable, parameter)
    }

    override fun valueToString(): String = "memory ${hashCode()}"

}
