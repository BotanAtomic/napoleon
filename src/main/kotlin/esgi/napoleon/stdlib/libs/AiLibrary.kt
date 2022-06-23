package esgi.napoleon.stdlib.libs

import esgi.napoleon.stdlib.Package
import esgi.napoleon.stdlib.libs.HttpLibrary.execute
import esgi.napoleon.stdlib.libs.HttpLibrary.json
import esgi.napoleon.stdlib.libs.HttpLibrary.params
import esgi.napoleon.stdlib.libs.JsonLibrary.init
import esgi.napoleon.stdlib.libs.JsonLibrary.keys
import esgi.napoleon.stdlib.libs.ListLibrary.append
import esgi.napoleon.stdlib.libs.ListLibrary.average
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.primitive.*
import esgi.napoleon.variables.primitive.api.NumberVariable

private val AI_API = System.getenv().getOrDefault("AI_API", "http://localhost:5000")

private val cache: MutableMap<String, Variable<*>> = hashMapOf()

@Package("ai")
object AiLibrary {


    fun sentiment(values: ListVariable, normalize: BooleanVariable = BooleanVariable(true)): Variable<*> {
        val result = ListVariable()
        values.value.forEach {
            if (cache.containsKey(it.valueToString())) {
                result.append(cache.getValue(it.valueToString()))
            } else {
                val request = HttpLibrary.get(StringVariable("$AI_API/sentiment"))
                val value = request.params(ObjectVariable(hashMapOf("query" to it))).execute().json()
                result.append(value)
                cache[it.valueToString()] = value;
            }
        }

        if (!normalize.value)
            return result

        val newResult = ListVariable()
        result.value.map {
            return@map if (it is ObjectVariable) {
                val score = it["score"] as FloatVariable
                val label = it["label"] as StringVariable

                 if (label.value == "NEGATIVE") score.multiply(IntegerVariable(-1))
                else score

            } else it
        }.filter { it is NumberVariable }.forEach { newResult.insert(it) }


        return newResult
    }

    fun emotion(values: ListVariable, merge: BooleanVariable = BooleanVariable(false)): Variable<*> {
        val result = ListVariable()
        values.value.forEach {
            val request = HttpLibrary.get(StringVariable("$AI_API/emotion"))

            result.append(request.params(ObjectVariable(hashMapOf("query" to it))).execute().json())
        }

        if (!merge.value)
            return result

        val mergedResult = ObjectVariable()
        mergedResult.init((result.getIndex(IntegerVariable(0)) as ObjectVariable).keys(), FloatVariable(0.0))

        result.value.forEach { value ->
            (value as ObjectVariable).keys().value.forEach { emotion ->
                mergedResult.setIndex(emotion, mergedResult.getIndex(emotion).add(value.getIndex(emotion)))
            }
        }

        return mergedResult
    }

}
