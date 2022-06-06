package esgi.napoleon.stdlib.libs

import esgi.napoleon.ExecutionEnvironment
import esgi.napoleon.stdlib.Environment
import esgi.napoleon.variables.Variable

object IOLibrary {

    fun print(
        vararg variables: Variable<*>,
        @Environment environment: esgi.napoleon.ExecutionEnvironment
    ) = environment.getLogger().log(variables.joinToString(" ") {
        it.valueToString()
    })

    fun println(
        vararg variables: Variable<*>,
        @Environment environment: esgi.napoleon.ExecutionEnvironment
    ) = environment.getLogger().log(variables.joinToString(" ") {
        it.valueToString()
    } + "\n")

}
