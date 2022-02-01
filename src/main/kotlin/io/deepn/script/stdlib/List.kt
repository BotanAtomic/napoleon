package io.deepn.script.stdlib

import io.deepn.script.variables.Variable
import io.deepn.script.variables.primitive.BooleanVariable
import io.deepn.script.variables.primitive.ListVariable

object List {

    fun ListVariable.contains(value : Variable<*>) = BooleanVariable(this.value.contains(value))

    fun ListVariable.reverse() = ListVariable(this.value.reversed())

    fun ListVariable.clear() = this.value.clear()

}
