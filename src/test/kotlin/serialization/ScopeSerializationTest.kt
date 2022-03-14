package serialization

import io.deepn.flow.DefaultExecutionEnvironment
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.scope.impl.DefaultScope
import io.deepn.flow.serialization.deserializeEnvironment
import io.deepn.flow.serialization.serializeEnvironment
import io.deepn.flow.variables.primitive.IntegerVariable
import io.deepn.flow.variables.primitive.ListVariable
import io.deepn.flow.variables.primitive.StringVariable
import org.junit.jupiter.api.Test

class ScopeSerializationTest {

    @Test
    fun serialize() {
        val code = """
            static b = initialB
            a = "a is " + (version + 1)
            
            storage.init("data", [])
            
            storage.get("data").insert(0, a)
            
            b += 1
            return [version, a, b]
        """.trimIndent()

        val initialScope = DefaultScope(initialVariables = VariableMap().apply {
            put("initialB", IntegerVariable(42))
        })
        val environment = DefaultExecutionEnvironment(code, scope = initialScope)

        assert(environment.compile().success)

        for (i in 0..4) {
            environment.execute()
        }

        var executionResult = environment.execute()

        if (executionResult.success.not())
            println(executionResult.error)

        val serializedEnv = serializeEnvironment(environment)

        val environmentData = deserializeEnvironment(serializedEnv)

        val newEnvironment =
            DefaultExecutionEnvironment(code, scope = environmentData.scope, storage = environmentData.storage)

        assert(newEnvironment.compile().success)

        for (i in 0..4) {
            newEnvironment.execute()
        }

        executionResult = newEnvironment.execute()

        if (executionResult.success.not())
            println(executionResult.error)

        val returnedResult = executionResult.result as ListVariable

        assert(returnedResult.value[0].eq(IntegerVariable(11)).value)
        assert(returnedResult.value[1].eq(StringVariable("a is 12")).value)
        assert(returnedResult.value[2].eq(IntegerVariable(54)).value)
    }

}
