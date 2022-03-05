package io.deepn.flow.utils

import io.deepn.flow.variables.FunctionArguments
import io.deepn.flow.variables.Variable
import java.util.*

fun createFunctionArguments(vararg arguments: Pair<String?, Variable<*>>): FunctionArguments {
    return LinkedList<Pair<String?, Variable<*>>>().apply {
        arguments.forEach { this.add(it) }
    }
}
