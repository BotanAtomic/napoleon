package io.deepn.flow.stdlib.libs

import io.deepn.flow.error.FlowError
import io.deepn.flow.error.TypeError
import io.deepn.flow.utils.createFunctionArguments
import io.deepn.flow.variables.Null
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.Void
import io.deepn.flow.variables.error.ErrorVariable
import io.deepn.flow.variables.function.LocalFunctionVariable
import io.deepn.flow.variables.primitive.BooleanVariable
import io.deepn.flow.variables.primitive.IntegerVariable
import io.deepn.flow.variables.primitive.ListVariable
import kotlin.math.absoluteValue
import kotlin.math.sign

object ListLibrary {

    fun list(size: IntegerVariable = IntegerVariable(1)) = ListVariable().apply {
        (0 until size.value).forEach { _ -> this.append(Null) }
    }

    fun ListVariable.fill(variable: Variable<*>) = this.apply {
        for (i in 0 until this.value.size) {
            this.value[i] = variable
        }
    }

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
            if (result.exceptionOrNull() != null && result.exceptionOrNull() is FlowError)
                return ErrorVariable(result.exceptionOrNull() as FlowError)

            return@map result.getOrNull()
        }.forEach {
            if (it != null)
                this.value.remove(it)
        }

        return Void
    }

    fun ListVariable.contains(value: Variable<*>) = BooleanVariable(this.value.find { it.eq(value).value } != null)

    fun ListVariable.reverse() = ListVariable(this.value.reversed())

    fun ListVariable.clear() = this.value.clear()

    fun ListVariable.map(transform: LocalFunctionVariable): Variable<*> {
        return ListVariable(
            this.value.map {
                val result = kotlin.runCatching {
                    transform.call(createFunctionArguments(Pair(null, it)))
                }
                if (result.exceptionOrNull() != null && result.exceptionOrNull() is FlowError)
                    return ErrorVariable(result.exceptionOrNull() as FlowError)

                result.getOrNull()
            }.filterNotNull()
        )
    }

    fun ListVariable.forEach(action: LocalFunctionVariable): Variable<*> {
        this.value.forEach {
            val result = kotlin.runCatching {
                action.call(createFunctionArguments(Pair(null, it)))
            }
            if (result.exceptionOrNull() != null && result.exceptionOrNull() is FlowError)
                return ErrorVariable(result.exceptionOrNull() as FlowError)
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
                if (result.exceptionOrNull() != null && result.exceptionOrNull() is FlowError)
                    return ErrorVariable(result.exceptionOrNull() as FlowError)

                result.getOrNull() == true
            }
        )
    }

    fun ListVariable.distinct(): ListVariable {
        return ListVariable(this.value.distinctBy { it.valueToString() })
    }

    fun ListVariable.extends(list: ListVariable): ListVariable {
        return ListVariable(ArrayList(this.value).apply { this.addAll(list.value) })
    }

    fun ListVariable.sort(reverse: BooleanVariable = BooleanVariable(false)): ListVariable =
        ListVariable(this.value.sortedBy { it }).let {
            return@let if (reverse.value) it.reverse() else it
        }

    fun ListVariable.shuffle() = ListVariable(this.value.shuffled())
}
