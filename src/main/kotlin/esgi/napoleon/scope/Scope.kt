package esgi.napoleon.scope

import esgi.napoleon.variables.Variable

typealias VariableMap = HashMap<String, Variable<*>>

interface Scope {

    fun implementsLibrary(library: VariableMap)

    fun getVariables(): VariableMap

    fun resolve(key: String, returnMemoryAddress: Boolean = false) : Variable<*>

    fun assign(key: String, value: Variable<*>)

    fun protectName(key: String)

    fun remove(key: String)

    fun createChildren(initialVariables: VariableMap) : Scope

}
