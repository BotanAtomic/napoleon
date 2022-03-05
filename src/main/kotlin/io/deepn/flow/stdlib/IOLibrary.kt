package io.deepn.flow.stdlib

import io.deepn.flow.DefaultExecutionEnvironment
import io.deepn.flow.variables.Variable

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
