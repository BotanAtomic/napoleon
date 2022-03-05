package io.deepn.flow.utils

import kotlin.math.abs

val EPSILON = Math.ulp(1.0)

fun Double.doubleEquals(second: Double): Boolean {
    return if (this == second) true else abs(this - second) < EPSILON
}
