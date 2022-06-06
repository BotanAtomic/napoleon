package esgi.napoleon.stdlib.libs

import esgi.napoleon.error.ValueError
import esgi.napoleon.utils.runOrValueError
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.primitive.FloatVariable
import esgi.napoleon.variables.primitive.IntegerVariable
import esgi.napoleon.variables.primitive.StringVariable

object UtilsLibrary {

    fun len(variable: Variable<*>) = variable.length()

    fun str(variable: Variable<*>) = StringVariable(variable.valueToString())

    fun int(variable: Variable<*>) = when (variable) {
        is FloatVariable -> IntegerVariable(variable.toLong())
        is IntegerVariable -> variable
        is StringVariable -> runOrValueError { IntegerVariable(variable.value.toLong()) }
        else -> throw ValueError("'${variable.type()}' cannot be converted to int")
    }

    fun float(variable: Variable<*>) = when (variable) {
        is FloatVariable -> variable
        is IntegerVariable -> FloatVariable(variable.toDouble())
        is StringVariable -> runOrValueError { FloatVariable(variable.value.toDouble()) }
        else -> throw ValueError("'${variable.type()}' cannot be converted to float")
    }

    fun bool(variable: Variable<*>) = variable.toBoolean()

    fun type(variable: Variable<*>) = StringVariable(variable.type())

}
