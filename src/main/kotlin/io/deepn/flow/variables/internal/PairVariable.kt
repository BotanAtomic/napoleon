package io.deepn.flow.variables.internal

import io.deepn.flow.variables.Variable

class PairVariable<K, V>(value: Pair<K, V>) : Variable<Pair<K, V>>(value) {

    override fun type() = "pair"

    fun key() : K = value.first

    fun value() : V = value.second
}
