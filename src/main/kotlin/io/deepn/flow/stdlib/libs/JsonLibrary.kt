package io.deepn.flow.stdlib.libs

import com.google.gson.JsonParser
import io.deepn.flow.utils.objectToJson
import io.deepn.flow.utils.parseJson
import io.deepn.flow.variables.primitive.BooleanVariable
import io.deepn.flow.variables.primitive.ListVariable
import io.deepn.flow.variables.primitive.ObjectVariable
import io.deepn.flow.variables.primitive.StringVariable

object JsonLibrary {

    fun ObjectVariable.stringify(prettyPrint: BooleanVariable = BooleanVariable(false)) =
        StringVariable(objectToJson(this, prettyPrint.value))

    fun StringVariable.toJson() = parseJson(this.value)

    fun StringVariable.isJson() = BooleanVariable(runCatching { JsonParser.parseString(this.value) }.isSuccess)

    fun ObjectVariable.has(key: StringVariable) = BooleanVariable(this.value.containsKey(key.value))

    fun ObjectVariable.keys(): ListVariable = ListVariable(this.value.keys.map { StringVariable(it) })

    fun ObjectVariable.values(): ListVariable = ListVariable(this.value.values)


}
