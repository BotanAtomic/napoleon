package esgi.napoleon.variables

import esgi.napoleon.error.FunctionCallError
import esgi.napoleon.error.TypeError
import esgi.napoleon.stdlib.StandardLibrary.findFunctionExtension
import esgi.napoleon.variables.date.DateTimeVariable
import esgi.napoleon.variables.date.DateVariable
import esgi.napoleon.variables.date.DurationVariable
import esgi.napoleon.variables.date.TimeVariable
import esgi.napoleon.variables.function.LocalFunctionVariable
import esgi.napoleon.variables.function.NativeFunctionVariable
import esgi.napoleon.variables.memory.IndexedVariable
import esgi.napoleon.variables.primitive.*
import esgi.napoleon.variables.ta.BarSeriesVariable
import java.util.*

object Null : Variable<Any>(Any()) {
    override fun type() = "null"
    override fun toString() = "null"
    override fun valueToString() = toString()
    override fun eq(variable: Variable<*>): BooleanVariable {
        return BooleanVariable(variable.type() == type())
    }
}

object Void : Variable<Any>(Any()) {
    override fun type() = "void"
    override fun toString() = "void"
    override fun valueToString() = toString()

    override fun eq(variable: Variable<*>): BooleanVariable {
        return BooleanVariable(variable == Void)
    }
}

typealias FunctionParameters = LinkedHashMap<String, (() -> Variable<*>)?>
typealias FunctionArguments = LinkedList<Pair<String?, Variable<*>>>?

abstract class Variable<T : Any>(val value: T) : Comparable<Variable<*>> {

    var name: String? = null

    abstract fun type(): String

    open fun valueToString(): String = value.toString()

    open fun add(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for +: '${type()}' and '${by.type()}'"
    )

    open fun subtract(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for -: '${type()}' and '${by.type()}'"
    )

    open fun multiply(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for *: '${type()}' and '${by.type()}'"
    )

    open fun power(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for **: '${type()}' and '${by.type()}'"
    )

    open fun divide(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for /: '${type()}' and '${by.type()}'"
    )

    open fun floorDivide(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for //: '${type()}' and '${by.type()}'"
    )

    open fun modulo(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for %: '${type()}' and '${by.type()}'"
    )

    open fun and(variable: Variable<*>): BooleanVariable = BooleanVariable(false)

    open fun or(variable: Variable<*>): BooleanVariable = BooleanVariable(false)

    open fun bitwiseLeftShift(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for <<: '${type()}' and '${by.type()}'"
    )

    open fun bitwiseRightShift(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for >>: '${type()}' and '${by.type()}'"
    )

    open fun bitwiseAnd(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for &: '${type()}' and '${by.type()}'"
    )

    open fun bitwiseOr(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for |: '${type()}' and '${by.type()}'"
    )

    open fun bitwiseXor(by: Variable<*>): Variable<*> = throw TypeError(
        "unsupported operand type(s) for ^: '${type()}' and '${by.type()}'"
    )

    open fun bitwiseInv(): Variable<*> = throw TypeError(
        "bad operand type for unary ~: '${type()}'"
    )

    open fun not(): BooleanVariable = throw TypeError(
        "bad operand type for unary not: '${type()}'"
    )

    open fun eq(variable: Variable<*>): BooleanVariable = BooleanVariable(false)

    open fun gt(variable: Variable<*>): BooleanVariable = throw TypeError(
        "'>' not supported between instances of '${type()}' and '${variable.type()}'"
    )

    open fun gte(variable: Variable<*>): BooleanVariable = throw TypeError(
        "'>=' not supported between instances of '${type()}' and '${variable.type()}'"
    )

    open fun lt(variable: Variable<*>): BooleanVariable = throw TypeError(
        "'<' not supported between instances of '${type()}' and '${variable.type()}'"
    )

    open fun lte(variable: Variable<*>): BooleanVariable = throw TypeError(
        "'<=' not supported between instances of '${type()}' and '${variable.type()}'"
    )

    fun getExtensionFunction(position: StringVariable): Variable<*> {
        return findFunctionExtension(this, position)
            ?: throw FunctionCallError("'${type()}' has no function ${position.value}()")
    }

    open fun getIndex(position: Variable<*>): Variable<*> = throw TypeError(
        "'${type()}' is not subscriptable"
    )

    open fun setIndex(position: Variable<*>, variable: Variable<*>): Variable<*> = throw TypeError(
        "'${type()}' does not support item assignment"
    )

    open fun deleteIndex(position: Variable<*>): Variable<*> = throw TypeError(
        "'${type()}' does not support item deletion"
    )

    open fun toBoolean(): BooleanVariable = BooleanVariable(true)

    open fun call(arguments: FunctionArguments): Variable<*> = throw TypeError(
        "'${type()}' is not callable"
    )

    open fun toIterator(): Iterator<Variable<*>> = throw TypeError(
        "'${type()}' is not iterable"
    )

    open fun length(): IntegerVariable = throw TypeError(
        "type '${type()}' has no length"
    )

    override fun toString(): String {
        return "Variable(value='${valueToString()}', type='${type()}', name=${name})"
    }

    open fun isSerializable(): Boolean = false

    override fun compareTo(other: Variable<*>): Int {
        return 0
    }
}

fun classToType(variableClass: Any?) = when (variableClass) {
    NativeFunctionVariable::class -> "native_function"
    LocalFunctionVariable::class -> "function"
    IndexedVariable::class -> "index"
    BooleanVariable::class -> "boolean"
    FloatVariable::class -> "float"
    IntegerVariable::class -> "integer"
    ListVariable::class -> "list"
    ObjectVariable::class -> "object"
    StringVariable::class -> "string"
    Null::class -> "null"
    Void::class -> "void"
    DateTimeVariable::class -> "date_time"
    DateVariable::class -> "date"
    TimeVariable::class -> "time"
    DurationVariable::class -> "duration"
    BarSeriesVariable::class.java -> "bar_series"
    else -> "unknown"
}
