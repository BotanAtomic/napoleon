package io.deepn.script.stdlib

import com.google.gson.JsonParser
import io.deepn.script.utils.objectToJson
import io.deepn.script.utils.parseJson
import io.deepn.script.variables.primitive.BooleanVariable
import io.deepn.script.variables.primitive.ObjectVariable
import io.deepn.script.variables.primitive.StringVariable

object JsonLibrary {

    fun ObjectVariable.stringify(prettyPrint: BooleanVariable = BooleanVariable(false)) =
        StringVariable(objectToJson(this, prettyPrint.value))

    fun StringVariable.toJson() = parseJson(this.value)

    fun StringVariable.isJson() = BooleanVariable(runCatching { JsonParser.parseString(this.value) }.isSuccess)

}
