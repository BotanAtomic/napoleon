package io.deepn.script.scope.impl

import io.deepn.script.error.NameError
import io.deepn.script.scope.Scope
import io.deepn.script.scope.VariableMap
import io.deepn.script.variables.Variable
import io.deepn.script.variables.Void
import io.deepn.script.variables.memory.MemoryAddressVariable
import io.deepn.script.variables.primitive.IntegerVariable

class BufferedScope(
    private val parent: Scope? = null,
    private val initialVariables: VariableMap? = null,
    private val maximumSnapshot: Int = 500
) : Scope {

    private val protectedNames = HashSet<String>()
    private val staticVersions = HashMap<String, Int>()

    private var version = 0

    private val staticVariables = VariableMap()

    private var library: VariableMap? = null

    private val variables = Array(maximumSnapshot) {
        createDefaultVariables(it)
    }

    init {
        initialVariables?.keys?.forEach { protectName(it) }
        protectName("version")
    }

    private fun getVariablesIndex(index: Int = 0): Int {
        if (index >= maximumSnapshot || index > version)
            return getVariablesIndex(0)

        return (version - minOf(index, version)) % maximumSnapshot
    }

    private fun createDefaultVariables(version: Int) = VariableMap().apply {
        if (initialVariables != null)
            putAll(initialVariables)
        put("version", IntegerVariable(version))
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
            variables[getVariablesIndex(0)] = createDefaultVariables(version)
    }

    override fun createChildren(initialVariables: VariableMap): Scope {
        return BufferedScope(this, initialVariables, maximumSnapshot)
    }

}
