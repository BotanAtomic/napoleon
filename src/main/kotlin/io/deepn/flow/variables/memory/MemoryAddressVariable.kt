package io.deepn.flow.variables.memory

import io.deepn.flow.variables.Variable

class MemoryAddressVariable(private val onAssign: (Variable<*>, Any?) -> Unit) : Variable<Any>(Any()) {

    override fun type() = "null"

    fun assign(variable: Variable<*>, parameter: Any? = null) {
        onAssign(variable, parameter)
    }

    override fun valueToString(): String = type()

}
