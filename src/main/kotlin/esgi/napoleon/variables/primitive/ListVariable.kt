package esgi.napoleon.variables.primitive

import esgi.napoleon.error.IndexError
import esgi.napoleon.error.TypeError
import esgi.napoleon.variables.Variable
import java.util.*

class ListVariable(
    list: Collection<Variable<*>> = LinkedList()
) : Variable<LinkedList<Variable<*>>>(LinkedList(list)) {

    override fun type() = "list"

    fun insert(variable: Variable<*>) {
        value.add(variable)
    }

    override fun eq(variable: Variable<*>): BooleanVariable {
        if (variable is ListVariable) {
            if (variable.value.size != value.size)
                return BooleanVariable(false)
            return BooleanVariable(value.zip(variable.value).all { (first, second) ->
                first.eq(second).value
            })
        }
        return super.eq(variable)
    }


    override fun getIndex(position: Variable<*>): Variable<*> {
        if (position is IntegerVariable) {
            val index = position.value.toInt()
            if (index < 0 || index >= value.size)
                throw IndexError("list index out of range: index=${index}, size=${value.size}")

            return value[index]
        }
        throw TypeError("list index must be integer, not '${position.type()}'")
    }


    override fun setIndex(position: Variable<*>, variable: Variable<*>): Variable<*> {
        if (position is IntegerVariable) {
            val index = position.value.toInt()
            if (index < 0 || index >= value.size)
                throw IndexError("list assignment index out of range: index=${index}, size=${value.size}")

            value[index] = variable
            return variable
        }
        throw TypeError("list index must be integer, not '${position.type()}'")
    }

    override fun deleteIndex(position: Variable<*>): Variable<*> {
        if (position is IntegerVariable) {
            val index = position.value.toInt()
            if (index < 0 || index >= value.size)
                throw IndexError("list assignment index out of range: index=${index}, size=${value.size}")

            return value.removeAt(index)
        }
        throw TypeError("list index must be integer, not '${position.type()}'")
    }

    override fun valueToString(): String {
        return value.joinToString(",", "[", "]") { it.valueToString() }
    }

    override fun toBoolean(): BooleanVariable {
        return BooleanVariable(value.isNotEmpty())
    }

    override fun toIterator() = value.iterator()

    override fun length(): IntegerVariable {
        return IntegerVariable(value.size)
    }

    override fun isSerializable() = true
}
