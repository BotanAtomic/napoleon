package esgi.napoleon.stdlib.libs

import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.core.isSuccessful
import esgi.napoleon.error.ValueError
import esgi.napoleon.stdlib.FunctionName
import esgi.napoleon.stdlib.Package
import esgi.napoleon.stdlib.libs.JsonLibrary.isJson
import esgi.napoleon.stdlib.libs.JsonLibrary.stringify
import esgi.napoleon.utils.parseJson
import esgi.napoleon.variables.Null
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.primitive.*

class HttpRequestVariable(private val request: Request) : Variable<Request>(request) {

    override fun type(): String = "HttpRequest_${request.method.value}"

    override fun valueToString(): String {
        return this.value.url.toString() + "//" + this.value.parameters.toString()
    }

}

class HttpResponseVariable(response: Response, exception: FuelError?) : Variable<Response>(response) {

    override fun type(): String = "HttpResponse"

    val url = StringVariable(response.url.toString())
    val statusCode = IntegerVariable(response.statusCode)
    val body = StringVariable(String(response.data))

    val length = IntegerVariable(response.contentLength)
    val headers = ObjectVariable()
    val isSuccessful = BooleanVariable(response.isSuccessful)

    val error = exception?.let { StringVariable(exception.message ?: "Unknown") } ?: Null

    init {
        response.headers.entries.forEach { (key, values) ->
            headers[key] = StringVariable(values.first())
        }
    }

    override fun length(): IntegerVariable {
        return length
    }

}

fun request(
    url: StringVariable,
    request: () -> Request
): HttpRequestVariable = try {
    HttpRequestVariable(request())
} catch (e: Exception) {
    throw ValueError("malformed url '${url.value}', ${e.message ?: "unknown"}")
}

@Package("http")
object HttpLibrary {

    fun get(url: StringVariable): HttpRequestVariable = request(url) { url.value.httpGet() }

    fun post(url: StringVariable): Variable<*> = request(url) { url.value.httpPost() }

    fun put(url: StringVariable): Variable<*> = request(url) { url.value.httpPut() }

    fun patch(url: StringVariable): Variable<*> = request(url) { url.value.httpPatch() }

    fun delete(url: StringVariable): Variable<*> = request(url) { url.value.httpDelete() }

    fun head(url: StringVariable): Variable<*> = request(url) { url.value.httpHead() }

    fun HttpRequestVariable.params(parameters: ObjectVariable): HttpRequestVariable {
        val requestParameters: MutableList<Pair<String, Any?>> = ArrayList()

        for ((key, variable) in parameters.value) {
            when (variable) {
                is StringVariable, is IntegerVariable, is FloatVariable -> requestParameters.add(key to variable.value)
                else -> requestParameters.add(key to variable.valueToString())
            }
        }

        this.value.parameters = requestParameters

        return this
    }

    fun HttpRequestVariable.headers(headers: ObjectVariable): Variable<*> {
        for ((key, variable) in headers.value) {
            variable.value
            when (variable) {
                is StringVariable, is IntegerVariable, is FloatVariable -> this.value.appendHeader(key, variable.value)
                else -> this.value.appendHeader(key, variable.valueToString())
            }
        }

        return this
    }

    fun HttpRequestVariable.body(body: Variable<*>): Variable<*> {
        when (body) {
            is ObjectVariable -> this.value.jsonBody(body.stringify().value)
            is StringVariable -> this.value.body(body.value)
            else -> this.value.body(body.valueToString())
        }
        return this
    }

    fun HttpRequestVariable.execute(
        timeout: IntegerVariable = IntegerVariable(5000),
        readTimeout: IntegerVariable = IntegerVariable(60000)
    ): HttpResponseVariable {
        val (_, response, error) = this.value.timeout(timeout.toInt()).timeoutRead(readTimeout.toInt()).response()

        val (_, exception) = error

        return HttpResponseVariable(response, exception)
    }

    fun HttpResponseVariable.text(): StringVariable = this.body

    fun HttpResponseVariable.json(): Variable<*> {
        return parseJson(this.text().value)
    }

    @FunctionName("is_json")
    fun HttpResponseVariable.isJson(): BooleanVariable = this.text().isJson()

    fun HttpResponseVariable.url() = this.url

    fun HttpResponseVariable.statusCode() = this.statusCode

    fun HttpResponseVariable.headers() = this.headers

    fun HttpResponseVariable.isSuccessful() = this.isSuccessful

    fun HttpResponseVariable.error() = this.error

}
