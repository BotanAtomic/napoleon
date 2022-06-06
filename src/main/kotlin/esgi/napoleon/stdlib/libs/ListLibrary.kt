package esgi.napoleon.stdlib.libs

import esgi.napoleon.error.NapoleonError
import esgi.napoleon.error.TypeError
import esgi.napoleon.error.ValueError
import esgi.napoleon.stdlib.FunctionName
import esgi.napoleon.utils.createFunctionArguments
import esgi.napoleon.variables.Null
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.Void
import esgi.napoleon.variables.function.LocalFunctionVariable
import esgi.napoleon.variables.primitive.BooleanVariable
import esgi.napoleon.variables.primitive.FloatVariable
import esgi.napoleon.variables.primitive.IntegerVariable
import esgi.napoleon.variables.primitive.ListVariable
import esgi.napoleon.variables.primitive.api.NumberVariable
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
            val returnedValue = filter.call(createFunctionArguments(Pair(null, it)))
            if (returnedValue !is BooleanVariable) throw TypeError("filter function must return a boolean value")

            return@map returnedValue
        }.forEach {
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
                transform.call(createFunctionArguments(Pair(null, it)))
            }
        )
    }

    fun ListVariable.forEach(action: LocalFunctionVariable): Variable<*> {
        this.value.forEach { action.call(createFunctionArguments(Pair(null, it))) }

        return Void
    }

    fun ListVariable.filter(filter: LocalFunctionVariable): Variable<*> {
        return ListVariable(
            this.value.filter {
                val returnedValue = filter.call(createFunctionArguments(Pair(null, it)))

                if (returnedValue !is BooleanVariable) throw TypeError("filter function must return a boolean value")

                returnedValue.value
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

    @FunctionName("avg")
    fun ListVariable.average(): Variable<*> {
        if (this.value.count { it !is NumberVariable } > 0) throw ValueError("value is not a list of numbers")
        return FloatVariable(this.value.map { (it as NumberVariable).toDouble() }.average())
    }
}
