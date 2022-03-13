package io.deepn.flow.serialization

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.deepn.flow.DefaultExecutionEnvironment
import io.deepn.flow.scope.Scope
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.scope.impl.DefaultScope
import io.deepn.flow.storage.Storage
import io.deepn.flow.storage.impl.DefaultStorage
import io.deepn.flow.variables.Null
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.Void
import io.deepn.flow.variables.date.DateTimeVariable
import io.deepn.flow.variables.date.DateVariable
import io.deepn.flow.variables.date.DurationVariable
import io.deepn.flow.variables.date.TimeVariable
import io.deepn.flow.variables.primitive.*
import java.lang.reflect.Type
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


private val gson = GsonBuilder()
    .registerTypeHierarchyAdapter(Variable::class.java, VariableSerializer())
    .registerTypeHierarchyAdapter(Variable::class.java, VariableDeserializer())
    .registerTypeHierarchyAdapter(Collection::class.java, CollectionSerializer())
    .registerTypeAdapter(DefaultExecutionEnvironment::class.java, DefaultExecutionEnvironmentSerializer())
    .registerTypeAdapter(DefaultExecutionEnvironmentData::class.java, DefaultExecutionEnvironmentDeserializer())
    .registerTypeAdapter(DefaultScope::class.java, DefaultScopeSerializer())
    .registerTypeAdapter(DefaultScope::class.java, DefaultScopeDeserializer())
    .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
    .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter())
    .registerTypeAdapter(LocalTime::class.java, LocalTimeTypeAdapter())
    .registerTypeAdapter(Duration::class.java, DurationTypeAdapter())
    .create()

data class DefaultExecutionEnvironmentData(val storage: Storage, val scope: Scope)

fun serializeEnvironment(environment: DefaultExecutionEnvironment): String = gson.toJson(environment)

fun deserializeEnvironment(input: String): DefaultExecutionEnvironmentData =
    gson.fromJson(input, DefaultExecutionEnvironmentData::class.java)


class DefaultExecutionEnvironmentSerializer : JsonSerializer<DefaultExecutionEnvironment> {
    override fun serialize(
        src: DefaultExecutionEnvironment,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val jsonObject = JsonObject()

        jsonObject.add("storage", context.serialize(src.getStorage()))
        jsonObject.add("scope", context.serialize(src.getScope()))
        return jsonObject
    }

}

class DefaultExecutionEnvironmentDeserializer : JsonDeserializer<DefaultExecutionEnvironmentData> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): DefaultExecutionEnvironmentData {
        val jsonObject = json.asJsonObject

        val storage: DefaultStorage = context.deserialize(jsonObject.get("storage")?.asJsonObject,
            object : TypeToken<DefaultStorage>() {}.type
        ) ?: DefaultStorage()

        val scope: DefaultScope = context.deserialize(jsonObject.get("scope")?.asJsonObject,
            object : TypeToken<DefaultScope>() {}.type
        ) ?: DefaultScope()

        return DefaultExecutionEnvironmentData(storage, scope)
    }

}

class DefaultScopeSerializer : JsonSerializer<DefaultScope> {
    override fun serialize(src: DefaultScope, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonObject = JsonObject()

        jsonObject.add("initialVariables", context.serialize(src.initialVariables))
        jsonObject.add("staticVersions", context.serialize(src.staticVersions))
        jsonObject.add("staticVariables", context.serialize(src.staticVariables))
        jsonObject.addProperty("version", src.version)

        return jsonObject
    }
}

class DefaultScopeDeserializer : JsonDeserializer<DefaultScope> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DefaultScope {
        val jsonObject = json.asJsonObject

        val initialVariables: VariableMap = context.deserialize(
            jsonObject.get("initialVariables")?.asJsonObject,
            object : TypeToken<VariableMap>() {}.type
        ) ?: VariableMap()

        val staticVersions: HashMap<String, Int> = context.deserialize(
            jsonObject.get("staticVersions").asJsonObject,
            object : TypeToken<HashMap<String, Int>>() {}.type
        )

        val staticVariables: VariableMap = context.deserialize(
            jsonObject.get("staticVariables").asJsonObject,
            object : TypeToken<VariableMap>() {}.type
        )

        val version = jsonObject.get("version").asInt

        return DefaultScope(
            parent = null,
            initialVariables,
            staticVersions,
            staticVariables,
            initialVersion = version
        )
    }
}

class VariableSerializer : JsonSerializer<Variable<*>> {
    override fun serialize(src: Variable<*>, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        if (!src.isSerializable())
            return JsonNull.INSTANCE

        val jsonObject = JsonObject()

        jsonObject.add("value", context.serialize(src.value))
        jsonObject.addProperty("type", src.type())
        jsonObject.addProperty("name", src.name)

        return jsonObject
    }
}

class VariableDeserializer : JsonDeserializer<Variable<*>> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Variable<*> {
        val jsonObject = json.asJsonObject
        val value = jsonObject.get("value")
        val name: String? = jsonObject.get("name")?.asString

        val variable = when (jsonObject.get("type").asString) {
            "boolean" -> BooleanVariable(value.asBoolean)
            "float" -> FloatVariable(value.asDouble)
            "integer" -> IntegerVariable(value.asLong)
            "list" -> ListVariable(
                context.deserialize(
                    value.asJsonArray,
                    object : TypeToken<List<Variable<*>>>() {}.type
                )
            )
            "object" -> ObjectVariable(
                context.deserialize(
                    value.asJsonObject,
                    object : TypeToken<VariableMap>() {}.type
                )
            )
            "string" -> StringVariable(value.asString)
            "null" -> Null
            "void" -> Void
            "date_time" -> DateTimeVariable(LocalDateTime.parse(value.asString))
            "date" -> DateVariable(LocalDate.parse(value.asString))
            "time" -> TimeVariable(LocalTime.parse(value.asString))
            "duration" -> DurationVariable(Duration.ofMillis(value.asLong))
            else -> Null
        }

        if (name != null && variable != Null && variable != Void)
            variable.name = name
        return variable
    }
}


class LocalDateTimeTypeAdapter : TypeAdapter<LocalDateTime>() {

    override fun write(out: JsonWriter, value: LocalDateTime) {
        out.value(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value))
    }

    override fun read(input: JsonReader): LocalDateTime = LocalDateTime.parse(input.nextString())
}

class LocalDateTypeAdapter : TypeAdapter<LocalDate>() {

    override fun write(out: JsonWriter, value: LocalDate) {
        out.value(DateTimeFormatter.ISO_LOCAL_DATE.format(value))
    }

    override fun read(input: JsonReader): LocalDate = LocalDate.parse(input.nextString())
}

class LocalTimeTypeAdapter : TypeAdapter<LocalTime>() {

    override fun write(out: JsonWriter, value: LocalTime) {
        out.value(DateTimeFormatter.ISO_LOCAL_TIME.format(value))
    }

    override fun read(input: JsonReader): LocalTime = LocalTime.parse(input.nextString())
}

class DurationTypeAdapter : TypeAdapter<Duration>() {

    override fun write(out: JsonWriter, value: Duration) {
        out.value(value.toMillis())
    }

    override fun read(input: JsonReader): Duration = Duration.ofMillis(input.nextLong())
}

class CollectionSerializer : JsonSerializer<Collection<*>> {
    override fun serialize(src: Collection<*>, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonArray = JsonArray()
        for (it in src) {
            if (it == null || (it is Variable<*> && !it.isSerializable())) continue
            jsonArray.add(context.serialize(it))
        }
        return jsonArray
    }

}
