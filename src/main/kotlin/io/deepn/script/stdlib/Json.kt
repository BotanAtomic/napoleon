package io.deepn.script.stdlib

import com.google.gson.*
import io.deepn.script.error.JsonError
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable
import io.deepn.script.variables.error.ErrorVariable
import io.deepn.script.variables.primitive.*
import java.math.BigDecimal
import kotlin.collections.List

val json: Gson = Gson().newBuilder().disableHtmlEscaping().create()
val jsonPrettyPrint: Gson = Gson().newBuilder().disableHtmlEscaping().setPrettyPrinting().create()

fun arrayToMap(arrayVariable: ListVariable): List<Any> {
    return arrayVariable.value.map { variable ->
        when (variable) {
            is ListVariable -> arrayToMap(variable)
            is BooleanVariable -> variable.value
            is FloatVariable -> variable.toDouble()
            is IntegerVariable -> variable.toInt()
            is StringVariable -> variable.value
            is ObjectVariable -> objectToMap(variable)
            else -> Null
        }
    }
}

fun objectToMap(objectVariable: ObjectVariable): Map<String, Any> {
    return objectVariable.value.map { (name, variable) ->
        name to when (variable) {
            is ListVariable -> arrayToMap(variable)
            is BooleanVariable -> variable.value
            is FloatVariable -> variable.toDouble()
            is IntegerVariable -> variable.value.toInt()
            is StringVariable -> variable.value
            is ObjectVariable -> objectToMap(variable)
            else -> Null
        }
    }.toMap()
}

fun objectToJson(variable: ObjectVariable, prettyPrint: Boolean): String {
    if (prettyPrint) return jsonPrettyPrint.toJson(objectToMap(variable))
    return json.toJson(objectToMap(variable))
}

private fun parseJsonPrimitive(jsonPrimitive: JsonPrimitive): Variable<*> {
    return when {
        jsonPrimitive.isString -> StringVariable(jsonPrimitive.asString)
        jsonPrimitive.isBoolean -> BooleanVariable(jsonPrimitive.asBoolean)
        jsonPrimitive.isNumber -> {
            val number = jsonPrimitive.asBigDecimal
            return if (number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0)
                IntegerVariable(number.toLong())
            else FloatVariable(number.toDouble())
        }
        else -> Null
    }
}

private fun parseJsonObject(jsonObject: JsonObject): Variable<*> {
    val variable = ObjectVariable()

    jsonObject.entrySet().forEach { (key, value) ->
        variable[key] = parseJsonElement(value)
    }

    return variable
}

private fun parseJsonArray(jsonArray: JsonArray): ListVariable {
    return ListVariable(ArrayList(jsonArray.map(::parseJsonElement)))
}

private fun parseJsonElement(element: JsonElement): Variable<*> {
    return when {
        element.isJsonObject -> parseJsonObject(element.asJsonObject)
        element.isJsonArray -> parseJsonArray(element.asJsonArray)
        element.isJsonPrimitive -> parseJsonPrimitive(element.asJsonPrimitive)
        element.isJsonNull -> Null
        else -> Null
    }
}

fun parseJson(input: String): Variable<*> {
    val result = runCatching { JsonParser.parseString(input) }

    result.exceptionOrNull()?.let { exception ->
        return ErrorVariable(JsonError(exception.cause?.message ?: "invalid json"))
    }
    return result.getOrNull()?.let { parseJsonElement(it) } ?: Null
}

object Json {


    fun ObjectVariable.stringify(prettyPrint: BooleanVariable = BooleanVariable(false)) =
        StringVariable(objectToJson(this, prettyPrint.value))

    fun StringVariable.toJson() = parseJson(this.value)

    fun StringVariable.isJson() = BooleanVariable(runCatching { JsonParser.parseString(this.value) }.isSuccess)

}
