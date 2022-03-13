package io.deepn.flow.storage.impl

import io.deepn.flow.scope.VariableMap
import io.deepn.flow.storage.Storage
import io.deepn.flow.variables.Null
import io.deepn.flow.variables.Variable

class DefaultStorage : Storage {

    private val variables = VariableMap()

    override fun init(key: String, variable: Variable<*>) {
        if (get(key) == Null)
            set(key, variable)
    }

    override fun get(key: String): Variable<*> = variables[key] ?: Null

    override fun set(key: String, variable: Variable<*>) {
        variables[key] = variable
    }

}
