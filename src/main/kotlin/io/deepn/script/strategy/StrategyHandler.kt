package io.deepn.script.strategy

import io.deepn.script.error.NameError
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class StrategyFunction

typealias StrategyFunctionArguments = Map<String, Variable<*>>

open class StrategyHandler {

    private val functions = this::class.functions.filter {
        it.hasAnnotation<StrategyFunction>()
    }.associateBy { it.name.lowercase() }.toMutableMap()

    fun call(id: String, parameters: StrategyFunctionArguments): Variable<*> {
        if (functions.containsKey(id.lowercase()).not())
            throw NameError("there is no strategy function named '$id'")
        return (functions[id.lowercase()]?.call(this, parameters) ?: Null) as Variable<*>
    }

}

class EmptyStrategyHandler : StrategyHandler()
