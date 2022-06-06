package esgi.napoleon.stdlib.libs

import esgi.napoleon.stdlib.Package
import esgi.napoleon.stdlib.libs.HttpLibrary.execute
import esgi.napoleon.stdlib.libs.HttpLibrary.json
import esgi.napoleon.stdlib.libs.HttpLibrary.params
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.primitive.ObjectVariable
import esgi.napoleon.variables.primitive.StringVariable

private val AI_API = System.getenv().getOrDefault("AI_API", "http://localhost:5000")

@Package("ai")
object AiLibrary {

    fun sentiment(value : StringVariable) : Variable<*> {
        val request = HttpLibrary.get(StringVariable("$AI_API/sentiment"))

        return if(request is HttpRequestVariable) {
            request.params(ObjectVariable(hashMapOf("query" to value))).execute().json()
        } else {
            request
        }
    }

    fun emotion(value : StringVariable) : Variable<*> {
        val request = HttpLibrary.get(StringVariable("$AI_API/emotion"))

        return if(request is HttpRequestVariable) {
            request.params(ObjectVariable(hashMapOf("query" to value))).execute().json()
        } else {
            request
        }
    }

}
