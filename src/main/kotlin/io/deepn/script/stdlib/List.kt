package io.deepn.script.stdlib

import io.deepn.script.error.DeepScriptError
import io.deepn.script.error.TypeError
import io.deepn.script.stdlib.List.distinct
import io.deepn.script.stdlib.List.extends
import io.deepn.script.utils.createFunctionArguments
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable
import io.deepn.script.variables.Void
import io.deepn.script.variables.error.ErrorVariable
import io.deepn.script.variables.function.LocalFunctionVariable
import io.deepn.script.variables.primitive.BooleanVariable
import io.deepn.script.variables.primitive.IntegerVariable
import io.deepn.script.variables.primitive.ListVariable
import kotlin.math.absoluteValue
import kotlin.math.sign

object List {

    fun ListVariable.append(variable: Variable<*>) = this.insert(variable)

    fun ListVariable.insert(position: IntegerVariable, variable: Variable<*>) {
        var adjustedPosition = position.value.toInt()
        val bound = this.value.size

        if (adjustedPosition.absoluteValue >= bound)
            adjustedPosition = bound * sign(adjustedPosition.toFloat()).toInt()

        if (adjustedPosition < 0)
            adjustedPosition += bound

        this.value.add(adjustedPosition, variable)
    }

    fun ListVariable.remove(variable: Variable<*>): Variable<*> {
        return if (this.value.firstOrNull { it.eq(variable).value }
                ?.let { this.value.remove(it) } == true) variable else Null
    }

    fun ListVariable.removeAll(variable: Variable<*>): BooleanVariable {
        return BooleanVariable(this.value.removeIf { it.eq(variable).value })
    }

    fun ListVariable.removeIf(filter: LocalFunctionVariable): Variable<*> {
        this.value.map {
            val result = kotlin.runCatching {
                val returnedValue = filter.call(createFunctionArguments(Pair(null, it)))
                if (returnedValue !is BooleanVariable) return ErrorVariable(TypeError("filter function must return a boolean value"))
                if (returnedValue.value) it else null
            }
            if (result.exceptionOrNull() != null && result.exceptionOrNull() is DeepScriptError)
                return ErrorVariable(result.exceptionOrNull() as DeepScriptError)

            return@map result.getOrNull()
        }.forEach {
            if (it != null)
                this.value.remove(it)
        }

        return Void
    }

    fun ListVariable.contains(value: Variable<*>) = BooleanVariable(this.value.contains(value))

    fun ListVariable.reverse() = ListVariable(this.value.reversed())

    fun ListVariable.clear() = this.value.clear()

    fun ListVariable.map(transform: LocalFunctionVariable): Variable<*> {
        return ListVariable(
            this.value.map {
                val result = kotlin.runCatching {
                    transform.call(createFunctionArguments(Pair(null, it)))
                }
                if (result.exceptionOrNull() != null && result.exceptionOrNull() is DeepScriptError)
                    return ErrorVariable(result.exceptionOrNull() as DeepScriptError)

                result.getOrNull()
            }.filterNotNull()
        )
    }

    fun ListVariable.forEach(action: LocalFunctionVariable): Variable<*> {
        this.value.forEach {
            val result = kotlin.runCatching {
                action.call(createFunctionArguments(Pair(null, it)))
            }
            if (result.exceptionOrNull() != null && result.exceptionOrNull() is DeepScriptError)
                return ErrorVariable(result.exceptionOrNull() as DeepScriptError)
        }

        return Void
    }

    fun ListVariable.filter(filter: LocalFunctionVariable): Variable<*> {
        return ListVariable(
            this.value.filter {
                val result = kotlin.runCatching {
                    val returnedValue = filter.call(createFunctionArguments(Pair(null, it)))
                    if (returnedValue !is BooleanVariable) return ErrorVariable(TypeError("filter function must return a boolean value"))
                    returnedValue.value
                }
                if (result.exceptionOrNull() != null && result.exceptionOrNull() is DeepScriptError)
                    return ErrorVariable(result.exceptionOrNull() as DeepScriptError)

                result.getOrNull() == true
            }
        )
    }

    fun ListVariable.distinct() : ListVariable {
        return ListVariable(this.value.distinctBy { it.valueToString() })
    }

    fun ListVariable.extends(list: ListVariable) : ListVariable {
        return ListVariable(ArrayList(this.value).apply { this.addAll(list.value) })
    }

}
