package esgi.napoleon.variables.memory

import esgi.napoleon.variables.Variable

class IndexedVariable(
    private val parent: Variable<*>,
    val variable: Variable<*>,
    val index: Variable<*>,
) : Variable<Any>(Any()) {

    override fun type() = "index"

    fun replace(newValue: Variable<*>) {
        parent.setIndex(index, newValue)
    }

    override fun valueToString(): String {
        return "index ${index.value}"
    }

}
