package esgi.napoleon.stdlib.libs

import esgi.napoleon.utils.runOrValueError
import esgi.napoleon.variables.primitive.BooleanVariable
import esgi.napoleon.variables.primitive.IntegerVariable
import esgi.napoleon.variables.primitive.StringVariable

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

    fun StringVariable.contains(value: StringVariable, ignoreCase: BooleanVariable = BooleanVariable(false)) =
        BooleanVariable(this.value.contains(value.value, ignoreCase.value))


}
