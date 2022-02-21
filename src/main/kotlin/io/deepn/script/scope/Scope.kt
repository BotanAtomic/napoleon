package io.deepn.script.scope

import io.deepn.script.variables.Variable

typealias VariableMap = HashMap<String, Variable<*>>

interface Scope {

    fun implementsLibrary(library: VariableMap)

    fun getVariables(index: Int = 0): VariableMap

    fun resolve(key: String, returnMemoryAddress: Boolean = false, index: Int = 0) : Variable<*>

    fun assign(key: String, value: Variable<*>, isStatic: Boolean = false)

    fun remove(key: String)

    fun snapshot()

    fun createChildren(initialVariables: VariableMap) : Scope
}
