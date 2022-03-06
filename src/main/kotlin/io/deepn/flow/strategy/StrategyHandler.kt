package io.deepn.flow.strategy

import io.deepn.flow.error.NameError
import io.deepn.flow.variables.Null
import io.deepn.flow.variables.Variable
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class StrategyFunction

typealias StrategyFunctionArguments = Map<String, Variable<*>>

abstract class StrategyHandler {

    protected val functions = HashMap<String, (StrategyFunctionArguments) -> Variable<*>>()

    fun call(id: String, parameters: StrategyFunctionArguments): Variable<*> {
        if (functions.containsKey(id.lowercase()).not())
            throw NameError("there is no strategy function named '$id'")
        val strategyFunction = functions[id.lowercase()]

        if(strategyFunction != null)
            return strategyFunction(parameters)

        return Null
    }

}

class EmptyStrategyHandler : StrategyHandler() {

    init {
        functions["buy"] = {
            Null
        }

        functions["sell"] = {
            Null
        }
    }

}
