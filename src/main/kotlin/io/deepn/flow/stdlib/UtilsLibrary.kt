package io.deepn.flow.stdlib

import io.deepn.flow.DefaultExecutionEnvironment
import io.deepn.flow.error.NameError
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.error.ErrorVariable
import io.deepn.flow.variables.primitive.BooleanVariable
import io.deepn.flow.variables.primitive.IntegerVariable
import io.deepn.flow.variables.primitive.StringVariable

object UtilsLibrary {

    fun len(variable: Variable<*>) = variable.length()

    fun str(variable: Variable<*>) = StringVariable(variable.valueToString())

    //TODO: to float and int
//    fun int(variable: Variable<*>) = StringVariable(variable.valueToString())
//
//    fun float(variable: Variable<*>) = StringVariable(variable.valueToString())

    fun bool(variable: Variable<*>) = variable.toBoolean()

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
