package io.deepn.flow.variables.function

import io.deepn.flow.error.KeyError
import io.deepn.flow.variables.Null
import io.deepn.flow.variables.Variable

class LibraryVariable(private val libraryName: String) : Variable<MutableMap<String, Variable<*>>>(HashMap()) {

    override fun type() = "'${libraryName}' library"

    override fun getIndex(position: Variable<*>): Variable<*> {
        val key = position.valueToString()
        if (!value.containsKey(key))
            throw KeyError("library '${libraryName}' has no function $key()")
        return value.getOrDefault(key, Null)
    }

    override fun setIndex(position: Variable<*>, variable: Variable<*>): Variable<*> {
        value[position.valueToString()] = variable
        return variable
    }

}
