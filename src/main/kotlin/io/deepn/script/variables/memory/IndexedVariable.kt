package io.deepn.script.variables.memory

import io.deepn.script.variables.Variable

class IndexedVariable(
    private val parent: Variable<*>,
    val variable: Variable<*>,
    private val index: Variable<*>,
) : Variable<Any>(Any()) {

    override fun type() = "index"

    fun replace(newValue: Variable<*>) {
        parent.setIndex(index, newValue)
    }

}
