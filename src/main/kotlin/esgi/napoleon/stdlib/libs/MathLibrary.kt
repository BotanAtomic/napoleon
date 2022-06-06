package esgi.napoleon.stdlib.libs

import esgi.napoleon.error.ValueError
import esgi.napoleon.stdlib.FunctionName
import esgi.napoleon.stdlib.Package
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.primitive.FloatVariable
import esgi.napoleon.variables.primitive.IntegerVariable
import esgi.napoleon.variables.primitive.ListVariable
import esgi.napoleon.variables.primitive.ObjectVariable
import esgi.napoleon.variables.primitive.api.NumberVariable
import esgi.napoleon.variables.stats.LinearRegressionVariable
import org.apache.commons.math3.ml.clustering.DoublePoint
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer
import org.apache.commons.math3.stat.regression.SimpleRegression
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
        if (to.lte(from).value) throw ValueError("bound must be greater than origin")
        return IntegerVariable(nextLong(from.toLong(), to.toLong()))
    }

    fun random(
        from: NumberVariable = IntegerVariable(0),
        to: NumberVariable = IntegerVariable(1)
    ): Variable<*> {
        if (to.toDouble() <= from.toDouble()) throw ValueError("bound must be greater than origin")
        return FloatVariable(nextDouble(from.toDouble(), to.toDouble()))
    }

    @FunctionName("linear_regression")
    fun linearRegression(x: ListVariable, y: ListVariable): LinearRegressionVariable {
        val regression = SimpleRegression()
        if (x.value.count { it !is NumberVariable } > 0) throw ValueError("x must be a list of numbers")
        if (y.value.count { it !is NumberVariable } > 0) throw ValueError("y must be a list of numbers")

        if (y.value.size != x.value.size) throw ValueError("X and Y sizes are not the same")

        x.value.zip(y.value).map { it.first as NumberVariable to it.second as NumberVariable }.forEach { (x, y) ->
            println("Add data ${x.toDouble()} - ${y.toDouble()}")
            regression.addData(x.toDouble(), y.toDouble())
        }

        return LinearRegressionVariable(regression)
    }

    fun LinearRegressionVariable.predict(x: NumberVariable) = FloatVariable(this.value.predict(x.toDouble()))


    fun kmeans(x: ListVariable, y: ListVariable, k: IntegerVariable): ListVariable {
        if (x.value.count { it !is NumberVariable } > 0) throw ValueError("x must be a list of numbers")
        if (y.value.count { it !is NumberVariable } > 0) throw ValueError("y must be a list of numbers")

        if (y.value.size != x.value.size) throw ValueError("X and Y sizes are not the same")
        if (k.value <= 0) throw ValueError("K must be a positive number")
        val list = ArrayList<DoublePoint>()

        val kmeans = KMeansPlusPlusClusterer<DoublePoint>(k.toInt())
        x.value.zip(y.value).map { it.first as NumberVariable to it.second as NumberVariable }.forEach { (x, y) ->
            list.add(DoublePoint(doubleArrayOf(x.toDouble(), y.toDouble())))
        }

        val toReturn = ListVariable()

        kmeans.cluster(list).forEach {
            toReturn.insert(ObjectVariable(hashMapOf(
                "center" to ObjectVariable(hashMapOf(
                    "x" to FloatVariable(it.center.point[0]),
                    "y" to FloatVariable(it.center.point[1])
                )),
                "points" to ListVariable(it.points.map { point ->
                    ObjectVariable(hashMapOf(
                        "x" to FloatVariable(point.point[0]),
                        "y" to FloatVariable(point.point[1])
                    ))
                })
            )))
        }

        return toReturn
    }
}
