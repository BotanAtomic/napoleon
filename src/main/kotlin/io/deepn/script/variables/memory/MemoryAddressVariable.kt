package io.deepn.script.variables.memory

import io.deepn.script.variables.Variable

class MemoryAddressVariable(private val onAssign: (Variable<*>) -> Unit) : Variable<Any>(Any()) {

    override fun type() = "memory_address"

    fun assign(variable: Variable<*>) {
        onAssign(variable)
    }

}
