package io.deepn.flow.stdlib.libs

import io.deepn.flow.ExecutionEnvironment
import io.deepn.flow.stdlib.Environment
import io.deepn.flow.variables.Variable

object IOLibrary {

    fun print(
        vararg variables: Variable<*>,
        @Environment environment: ExecutionEnvironment
    ) = environment.getLogger().log(variables.joinToString(" ") {
        it.valueToString()
    })

    fun println(
        vararg variables: Variable<*>,
        @Environment environment: ExecutionEnvironment
    ) = environment.getLogger().log(variables.joinToString(" ") {
        it.valueToString()
    } + "\n")

}
