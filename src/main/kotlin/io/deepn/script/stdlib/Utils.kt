package io.deepn.script.stdlib

import io.deepn.script.DefaultExecutionEnvironment
import io.deepn.script.error.NameError
import io.deepn.script.variables.Variable
import io.deepn.script.variables.error.ErrorVariable
import io.deepn.script.variables.primitive.IntegerVariable
import io.deepn.script.variables.primitive.StringVariable

object Utils {

    fun len(variable: Variable<*>) = variable.length()

    fun str(variable: Variable<*>) = StringVariable(variable.valueToString())

    fun type(variable: Variable<*>) = StringVariable(variable.type())

    fun history(
        variable: Variable<*>,
        version: IntegerVariable = IntegerVariable(0),
        @Environment environment: DefaultExecutionEnvironment
    ) : Variable<*> {

        return variable.name?.let { environment.scope.resolve(it, false, version.toInt()) }?: ErrorVariable(
            NameError("variable is not in global scope")
        )
    }

}
