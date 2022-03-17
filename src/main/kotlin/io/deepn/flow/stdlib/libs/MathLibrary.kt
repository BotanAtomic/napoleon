package io.deepn.flow.stdlib.libs

import io.deepn.flow.error.ValueError
import io.deepn.flow.stdlib.Package
import io.deepn.flow.utils.toError
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.primitive.FloatVariable
import io.deepn.flow.variables.primitive.IntegerVariable
import io.deepn.flow.variables.primitive.api.NumberVariable
import kotlin.random.Random.Default.nextDouble
import kotlin.random.Random.Default.nextLong

@Package("math")
object MathLibrary {

    val PI = FloatVariable(kotlin.math.PI)

    fun sin(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.sin(x.toDouble()))

    fun cos(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.cos(x.toDouble()))

    fun tan(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.tan(x.toDouble()))

    fun asin(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.asin(x.toDouble()))

    fun acos(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.acos(x.toDouble()))

    fun atan(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.atan(x.toDouble()))

    fun atan2(y: NumberVariable, x: NumberVariable): Variable<*> =
        FloatVariable(kotlin.math.atan2(y.toDouble(), x.toDouble()))

    fun sinh(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.sinh(x.toDouble()))

    fun cosh(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.cosh(x.toDouble()))

    fun tanh(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.tanh(x.toDouble()))

    fun asinh(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.asinh(x.toDouble()))

    fun acosh(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.acosh(x.toDouble()))

    fun atanh(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.atanh(x.toDouble()))

    fun hypot(x: NumberVariable, y: NumberVariable): Variable<*> =
        FloatVariable(kotlin.math.hypot(x.toDouble(), y.toDouble()))

    fun sqrt(x: NumberVariable): Variable<*> = FloatVariable(kotlin.math.sqrt(x.toDouble()))

    fun exp(x: NumberVariable) = FloatVariable(kotlin.math.exp(x.toDouble()))

    fun expm1(x: NumberVariable) = FloatVariable(kotlin.math.expm1(x.toDouble()))

    fun log(x: NumberVariable, base: NumberVariable) =
        FloatVariable(kotlin.math.log(x.toDouble(), base.toDouble()))

    fun ln(x: NumberVariable) = FloatVariable(kotlin.math.ln(x.toDouble()))

    fun log10(x: NumberVariable) = FloatVariable(kotlin.math.log10(x.toDouble()))

    fun log2(x: NumberVariable) = FloatVariable(kotlin.math.log2(x.toDouble()))

    fun ln1p(x: NumberVariable) = FloatVariable(kotlin.math.ln1p(x.toDouble()))

    fun ceil(x: NumberVariable) = FloatVariable(kotlin.math.ceil(x.toDouble()))

    fun floor(x: NumberVariable) = FloatVariable(kotlin.math.floor(x.toDouble()))

    fun truncate(x: NumberVariable) = FloatVariable(kotlin.math.truncate(x.toDouble()))

    fun round(x: NumberVariable) = FloatVariable(kotlin.math.round(x.toDouble()))

    fun abs(x: NumberVariable) = FloatVariable(kotlin.math.abs(x.toDouble()))

    fun sign(x: NumberVariable) = FloatVariable(kotlin.math.sign(x.toDouble()))

    fun min(x: NumberVariable, variable2: NumberVariable) =
        FloatVariable(kotlin.math.min(x.toDouble(), variable2.toDouble()))

    fun max(x: NumberVariable, variable2: NumberVariable) =
        FloatVariable(kotlin.math.max(x.toDouble(), variable2.toDouble()))

    fun randomInt(
        from: IntegerVariable = IntegerVariable(Long.MIN_VALUE),
        to: IntegerVariable = IntegerVariable(Long.MAX_VALUE)
    ): Variable<*> {
        if (to.lte(from).value) return ValueError("bound must be greater than origin").toError()
        return IntegerVariable(nextLong(from.toLong(), to.toLong()))
    }

    fun random(
        from: NumberVariable = IntegerVariable(0),
        to: NumberVariable = IntegerVariable(1)
    ): Variable<*> {
        if (to.toDouble() <= from.toDouble()) return ValueError("bound must be greater than origin").toError()
        return FloatVariable(nextDouble(from.toDouble(), to.toDouble()))
    }
}
