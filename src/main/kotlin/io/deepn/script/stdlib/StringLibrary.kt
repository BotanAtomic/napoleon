package io.deepn.script.stdlib

import io.deepn.script.error.ValueError
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable
import io.deepn.script.variables.error.ErrorVariable
import io.deepn.script.variables.primitive.IntegerVariable
import io.deepn.script.variables.primitive.StringVariable

private fun safeRun(unit: () -> Variable<*>): Variable<*> {
    val result = runCatching(unit)

    return if (result.isSuccess)
        result.getOrNull() ?: Null
    else
        ErrorVariable(ValueError(result.exceptionOrNull()?.message ?: "unknown error"))
}

object StringLibrary {

    fun StringVariable.toUpperCase() = StringVariable(this.value.uppercase())

    fun StringVariable.toLowerCase() = StringVariable(this.value.lowercase())

    fun StringVariable.drop(n: IntegerVariable) = safeRun { StringVariable(this.value.drop(n.toInt())) }

    fun StringVariable.dropLast(n: IntegerVariable) = safeRun { StringVariable(this.value.dropLast(n.toInt())) }

    fun StringVariable.removePrefix(prefix: StringVariable) = StringVariable(this.value.removePrefix(prefix.value))

    fun StringVariable.removeSuffix(suffix: StringVariable) = StringVariable(this.value.removeSuffix(suffix.value))

    fun StringVariable.substring(
        startIndex: IntegerVariable,
        endIndex: IntegerVariable
    ) = safeRun { StringVariable(this.value.substring(startIndex.toInt(), endIndex.toInt())) }


}
