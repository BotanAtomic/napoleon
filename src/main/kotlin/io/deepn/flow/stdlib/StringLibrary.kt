package io.deepn.flow.stdlib

import io.deepn.flow.utils.runOrValueError
import io.deepn.flow.variables.primitive.IntegerVariable
import io.deepn.flow.variables.primitive.StringVariable

object StringLibrary {

    fun StringVariable.toUpperCase() = StringVariable(this.value.uppercase())

    fun StringVariable.toLowerCase() = StringVariable(this.value.lowercase())

    fun StringVariable.drop(n: IntegerVariable) = runOrValueError { StringVariable(this.value.drop(n.toInt())) }

    fun StringVariable.dropLast(n: IntegerVariable) = runOrValueError { StringVariable(this.value.dropLast(n.toInt())) }

    fun StringVariable.removePrefix(prefix: StringVariable) = StringVariable(this.value.removePrefix(prefix.value))

    fun StringVariable.removeSuffix(suffix: StringVariable) = StringVariable(this.value.removeSuffix(suffix.value))

    fun StringVariable.substring(
        startIndex: IntegerVariable,
        endIndex: IntegerVariable
    ) = runOrValueError { StringVariable(this.value.substring(startIndex.toInt(), endIndex.toInt())) }


}