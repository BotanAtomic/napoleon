package serialization

import io.deepn.flow.DefaultExecutionEnvironment
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.scope.impl.BufferedScope
import io.deepn.flow.serialization.deserializeScope
import io.deepn.flow.serialization.serializeScope
import io.deepn.flow.variables.primitive.IntegerVariable
import io.deepn.flow.variables.primitive.StringVariable
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class ScopeSerializationTest {

    @Test
    fun serialize() {
        val code = """

            function test()
                println("test")
            end

            static a = 0

            a += 1

            value = "this is index " + version

            dateTime = date.currentDateTime()
            _date = date.currentDate()
            time = date.currentTime()

            dateTimeDelta = date.currentDateTime() - dateTime
            timeDelta = date.currentTime() - time

            array = [1,2,3, timeDelta, dateTimeDelta, "Hello", [1,2,3, http.get("https://google.com")]]
            obj = { "hello" : 4, lol: { lol2: 4 } }

            a_history = [history(value, 1), history(value, 2)]

            #println("version:", version, "| a=", a, "| history=", a_history, history(array, 1))
        """.trimIndent()

        val defaultScope = BufferedScope(initialVariables = VariableMap().apply {
            put("hello", StringVariable("hello"))
        })
        val compiler = DefaultExecutionEnvironment(code, scope=defaultScope)

        assert(compiler.compile().success)

        for (i in 0..4)
            assert(compiler.execute().success)

        val path = Files.createTempFile(Paths.get(""), "scope", ".json")

        kotlin.runCatching {
            Files.write(path, serializeScope(compiler.scope as BufferedScope).toByteArray())

            val scope = deserializeScope(Files.readString(path))

            val newCompiler = DefaultExecutionEnvironment(code, scope)
            assert(newCompiler.compile().success)

            assert(newCompiler.scope.resolve("version").eq(IntegerVariable(5)).value)
            assert(newCompiler.scope.resolve("array", false, 2)
                .getIndex(IntegerVariable(2)).eq(IntegerVariable(3)).value)

            assert(newCompiler.execute().success)
            Files.deleteIfExists(path)
        }.exceptionOrNull()?.let {
            Files.deleteIfExists(path)
            throw Exception("serialization failed: ${it.message}", it)
        }
    }

}
