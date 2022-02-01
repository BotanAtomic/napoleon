package io.deepn.script.stdlib

import io.deepn.script.DeepScriptEnvironment
import io.deepn.script.variables.Variable

object IO {

    fun print(
        vararg variables: Variable<*>,
        @Environment environment: DeepScriptEnvironment
    ) = environment.logger.log(variables.joinToString(" ") {
        it.valueToString()
    })

    fun println(
        vararg variables: Variable<*>,
        @Environment environment: DeepScriptEnvironment
    ) = environment.logger.log(variables.joinToString(" ") {
        it.valueToString()
    } + "\n")

}
