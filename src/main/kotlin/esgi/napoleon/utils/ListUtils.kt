package esgi.napoleon.utils

import esgi.napoleon.variables.FunctionArguments
import esgi.napoleon.variables.Variable
import java.util.*

fun createFunctionArguments(vararg arguments: Pair<String?, Variable<*>>): FunctionArguments {
    return LinkedList<Pair<String?, Variable<*>>>().apply {
        arguments.forEach { this.add(it) }
    }
}
