package io.deepn.script.stdlib

import io.deepn.script.DefaultExecutionEnvironment
import io.deepn.script.variables.Variable

object IOLibrary {

    fun print(
        vararg variables: Variable<*>,
        @Environment environment: DefaultExecutionEnvironment
    ) = environment.logger.log(variables.joinToString(" ") {
        it.valueToString()
    })

    fun println(
        vararg variables: Variable<*>,
        @Environment environment: DefaultExecutionEnvironment
    ) = environment.logger.log(variables.joinToString(" ") {
        it.valueToString()
    } + "\n")

}
