package esgi.napoleon.variables.internal

import esgi.napoleon.variables.Variable

class PairVariable<K, V>(value: Pair<K, V>) : Variable<Pair<K, V>>(value) {

    override fun type() = "pair"

    fun key() : K = value.first

    fun value() : V = value.second
}
