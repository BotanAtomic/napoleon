package io.deepn.flow.serialization

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.scope.impl.BufferedScope
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
    .registerTypeAdapter(BufferedScope::class.java, BufferedScopeSerializer())
    .registerTypeAdapter(BufferedScope::class.java, BufferedScopeDeserializer())
    .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
    .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter())
    .registerTypeAdapter(LocalTime::class.java, LocalTimeTypeAdapter())
    .registerTypeAdapter(Duration::class.java, DurationTypeAdapter())
    .create()

fun serializeScope(scope: BufferedScope): String = gson.toJson(scope)

fun deserializeScope(input: String): BufferedScope = gson.fromJson(input, BufferedScope::class.java)

class BufferedScopeSerializer : JsonSerializer<BufferedScope> {
    override fun serialize(src: BufferedScope, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonObject = JsonObject()

        jsonObject.add("initialVariables", context.serialize(src.initialVariables))
        jsonObject.addProperty("maximumSnapshot", src.maximumSnapshot)
        jsonObject.add("protectedNames", context.serialize(src.protectedNames))
        jsonObject.add("staticVersions", context.serialize(src.staticVersions))
        jsonObject.add("staticVariables", context.serialize(src.staticVariables))
        jsonObject.add("variables", context.serialize(src.variables))
        jsonObject.addProperty("version", src.version)

        return jsonObject
    }

}

class BufferedScopeDeserializer : JsonDeserializer<BufferedScope> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): BufferedScope {
        val jsonObject = json.asJsonObject

        val initialVariables: VariableMap? = context.deserialize(jsonObject.get("initialVariables")?.asJsonObject,
            object : TypeToken<VariableMap>() {}.type
        )

        val maximumSnapshot = jsonObject.get("maximumSnapshot").asInt

        val protectedNames: HashSet<String> = context.deserialize(jsonObject.get("protectedNames").asJsonArray,
            object : TypeToken<HashSet<String>>() {}.type
        )

        val staticVersions: HashMap<String, Int> = context.deserialize(jsonObject.get("staticVersions").asJsonObject,
            object : TypeToken<HashMap<String, Int>>() {}.type
        )

        val staticVariables: VariableMap = context.deserialize(jsonObject.get("staticVariables").asJsonObject,
            object : TypeToken<VariableMap>() {}.type
        )

        val variables: Array<VariableMap> = context.deserialize(jsonObject.get("variables").asJsonArray,
            object : TypeToken<Array<VariableMap>>() {}.type
        )

        val version = jsonObject.get("version").asInt

        return BufferedScope(
            parent = null,
            initialVariables,
            maximumSnapshot,
            protectedNames,
            staticVersions,
            staticVariables,
            variables
        ).apply { this.version = version }
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
            if(it == null || (it is Variable<*> && !it.isSerializable())) continue
            jsonArray.add(context.serialize(it))
        }
        return jsonArray
    }

}
