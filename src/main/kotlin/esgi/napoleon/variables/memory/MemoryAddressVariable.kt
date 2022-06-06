package esgi.napoleon.variables.memory

import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.primitive.BooleanVariable

class MemoryAddressVariable(private val onAssign: (Variable<*>, Any?) -> Unit) : Variable<Any>(Any()) {

    override fun type() = "null"

    fun assign(variable: Variable<*>, parameter: Any? = null) {
        onAssign(variable, parameter)
    }

    override fun valueToString(): String = type()

    override fun eq(variable: Variable<*>): BooleanVariable {
        return BooleanVariable(variable.type() == type())
    }

}
