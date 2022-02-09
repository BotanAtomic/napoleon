package io.deepn.script.utils

import io.deepn.script.error.DeepScriptError
import io.deepn.script.variables.Variable
import io.deepn.script.variables.error.ErrorVariable
import io.deepn.script.variables.memory.IndexedVariable
import io.deepn.script.variables.primitive.FloatVariable
import io.deepn.script.variables.primitive.IntegerVariable


fun Variable<*>.detach(): Variable<*> = if (this is IndexedVariable) this.variable else this

fun Variable<*>.isZero(): Boolean = when (this) {
    is IntegerVariable -> this.value == 0L
    is FloatVariable -> this.value.doubleEquals(0.0)
    else -> false
}

fun Variable<*>.isPositive(): Boolean = when (this) {
    is IntegerVariable -> this.value > 0L
    is FloatVariable -> this.value > 0.0
    else -> false
}

fun Variable<*>.isNegative(): Boolean = when (this) {
    is IntegerVariable -> this.value < 0L
    is FloatVariable -> this.value < 0.0
    else -> false
}


fun createForLoop(initial: Variable<*>, limit: Variable<*>, step: Variable<*>, callback: (Variable<*>) -> Boolean) {
    if (initial.lt(limit).value) {
        var i = initial
        while (i.lte(limit).value) {
            if (!callback(i))
                break
            i = i.add(step)
        }
    } else {
        var i = initial
        while (i.gte(limit).value) {
            if (!callback(i))
                break
            i = i.add(step)
        }
    }
}

fun Boolean.toProducer(producer: () -> Variable<*>): (() -> Variable<*>)? {
    return if (this) {
        { producer() }
    } else null
}

fun DeepScriptError.toError() = ErrorVariable(this)

