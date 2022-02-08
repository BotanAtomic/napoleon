package io.deepn.script.stdlib

import io.deepn.script.variables.Variable
import io.deepn.script.variables.primitive.StringVariable

object Utils {

    fun len(variable :Variable<*>) = variable.length()

    fun str(variable: Variable<*>) = StringVariable(variable.valueToString())

    fun type(variable: Variable<*>) = StringVariable(variable.type())
}
