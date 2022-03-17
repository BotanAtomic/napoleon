package io.deepn.flow.stdlib.libs

import io.deepn.flow.error.ValueError
import io.deepn.flow.utils.runOrValueError
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.error.ErrorVariable
import io.deepn.flow.variables.primitive.FloatVariable
import io.deepn.flow.variables.primitive.IntegerVariable
import io.deepn.flow.variables.primitive.StringVariable

object UtilsLibrary {

    fun len(variable: Variable<*>) = variable.length()

    fun str(variable: Variable<*>) = StringVariable(variable.valueToString())

    fun int(variable: Variable<*>) = when(variable) {
        is FloatVariable -> IntegerVariable(variable.toLong())
        is IntegerVariable -> variable
        is StringVariable -> runOrValueError { IntegerVariable(variable.value.toLong()) }
        else -> ErrorVariable(ValueError("'${variable.type()}' cannot be converted to int"))
    }

    fun float(variable: Variable<*>) = when(variable) {
        is FloatVariable -> variable
        is IntegerVariable -> FloatVariable(variable.toDouble())
        is StringVariable -> runOrValueError { FloatVariable(variable.value.toDouble()) }
        else -> ErrorVariable(ValueError("'${variable.type()}' cannot be converted to float"))
    }

    fun bool(variable: Variable<*>) = variable.toBoolean()

    fun type(variable: Variable<*>) = StringVariable(variable.type())

}
