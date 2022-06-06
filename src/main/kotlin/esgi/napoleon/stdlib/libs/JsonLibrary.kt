package esgi.napoleon.stdlib.libs

import com.google.gson.JsonParser
import esgi.napoleon.utils.objectToJson
import esgi.napoleon.utils.parseJson
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.primitive.BooleanVariable
import esgi.napoleon.variables.primitive.ListVariable
import esgi.napoleon.variables.primitive.ObjectVariable
import esgi.napoleon.variables.primitive.StringVariable

object JsonLibrary {

    fun ObjectVariable.stringify(prettyPrint: BooleanVariable = BooleanVariable(false)) =
        StringVariable(objectToJson(this, prettyPrint.value))

    fun StringVariable.toJson() = parseJson(this.value)

    fun StringVariable.isJson() = BooleanVariable(runCatching { JsonParser.parseString(this.value) }.isSuccess)

    fun ObjectVariable.has(key: StringVariable) = BooleanVariable(this.value.containsKey(key.value))

    fun ObjectVariable.keys(): ListVariable = ListVariable(this.value.keys.map { StringVariable(it) })

    fun ObjectVariable.values(): ListVariable = ListVariable(this.value.values)

    fun ObjectVariable.init(keys: ListVariable, value: Variable<*>) {
        keys.value.forEach { this.value[it.valueToString()] = value }
    }


}
