package io.deepn.flow.scope.impl

import io.deepn.flow.error.NameError
import io.deepn.flow.scope.Scope
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.Void
import io.deepn.flow.variables.memory.MemoryAddressVariable
import io.deepn.flow.variables.primitive.IntegerVariable

private fun createDefaultVariables(initialVariables: VariableMap?, version: Int) = VariableMap().apply {
    if (initialVariables != null)
        putAll(initialVariables)
    put("version", IntegerVariable(version))
}

open class BufferedScope(
    private val parent: Scope? = null,
    val initialVariables: VariableMap? = null,
    val maximumSnapshot: Int = 500,
    val protectedNames: HashSet<String> = HashSet(),
    val staticVersions: HashMap<String, Int> = HashMap(),
    val staticVariables: VariableMap = VariableMap(),
    val variables: Array<VariableMap> = Array(maximumSnapshot) {
        createDefaultVariables(initialVariables, it)
    }
) : Scope, java.io.Serializable {

    var version = 0
    private var library: VariableMap? = null

    init {
        initialVariables?.keys?.forEach { protectName(it) }
        protectName("version")
    }

    private fun getVariablesIndex(index: Int = 0): Int {
        if (index >= maximumSnapshot || index > version)
            return getVariablesIndex(0)

        return (version - minOf(index, version)) % maximumSnapshot
    }

    private fun protectName(key: String) {
        protectedNames.add(key)
    }

    override fun implementsLibrary(library: VariableMap) {
        library.keys.forEach { protectName(it) }
        this.library = library
    }


    override fun getVariables(index: Int): VariableMap {
        return variables.getOrElse(getVariablesIndex(index)) {
            variables[getVariablesIndex(0)]
        }
    }

    override fun resolve(key: String, returnMemoryAddress: Boolean, index: Int): Variable<*> {
        val variables = getVariables(index)

        val resolvedVariable = variables[key] ?: library?.get(key) ?: staticVariables[key]

        if(resolvedVariable != null && !resolvedVariable.isSerializable() && index != 0)
            return Void

        if (resolvedVariable == null && parent != null)
            return parent.resolve(key, returnMemoryAddress)

        if (resolvedVariable == null && returnMemoryAddress)
            return MemoryAddressVariable { value, isStatic ->
                assign(key, value, if (isStatic is Boolean) isStatic else false)
            }

        resolvedVariable?.name = key

        return resolvedVariable ?: Void
    }

    override fun assign(key: String, value: Variable<*>, isStatic: Boolean) {
        val variables = getVariables()
        if (isStatic && staticVersions[key] == version) throw NameError("name '$key' is already static")
        if (protectedNames.contains(key)) throw NameError("name '$key' is protected")

        val baseIsStatic = staticVersions.containsKey(key)

        if (isStatic) staticVersions[key] = version

        if (baseIsStatic && !isStatic || isStatic && !baseIsStatic)
            staticVariables[key] = value
        else if (!isStatic)
            variables[key] = value

        value.name = key
    }

    override fun remove(key: String) {
        getVariables().remove(key)
    }

    override fun snapshot() {
        version++

        if (version >= maximumSnapshot)
            variables[getVariablesIndex(0)] = createDefaultVariables(initialVariables, version)
    }

    override fun createChildren(initialVariables: VariableMap): Scope {
        return BufferedScope(this, initialVariables, maximumSnapshot)
    }

}
