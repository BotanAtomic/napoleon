package io.deepn.script.variables.function

import io.deepn.script.error.KeyError
import io.deepn.script.variables.Null
import io.deepn.script.variables.Variable

class LibraryVariable(private val libraryName: String) : Variable<MutableMap<String, NativeFunctionVariable>>(HashMap()) {

    override fun type() = "'${libraryName}' library"

    override fun getIndex(position: Variable<*>): Variable<*> {
        val key = position.valueToString()
        if (!value.containsKey(key))
            throw KeyError("library '${libraryName}' has no function $key()")
        return value.getOrDefault(key, Null)
    }

    override fun setIndex(position: Variable<*>, variable: Variable<*>): Variable<*> {
        value[position.valueToString()] = variable as NativeFunctionVariable
        return variable
    }

}
