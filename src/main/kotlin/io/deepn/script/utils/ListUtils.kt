package io.deepn.script.utils

import io.deepn.script.variables.FunctionArguments
import io.deepn.script.variables.Variable
import java.util.*

fun createFunctionArguments(vararg arguments: Pair<String?, Variable<*>>): FunctionArguments {
    return LinkedList<Pair<String?, Variable<*>>>().apply {
        arguments.forEach { this.add(it) }
    }
}
