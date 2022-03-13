package io.deepn.flow.scope.impl

import io.deepn.flow.error.NameError
import io.deepn.flow.error.ValueError
import io.deepn.flow.scope.Scope
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.Void
import io.deepn.flow.variables.memory.MemoryAddressVariable
import io.deepn.flow.variables.primitive.IntegerVariable

class DefaultScope(
    private val parent: Scope? = null,
    val initialVariables: VariableMap = VariableMap(),
    val staticVersions: HashMap<String, Int> = HashMap(),
    val staticVariables: VariableMap = VariableMap(),
    initialVersion : Int = 0
) : Scope {

    var version = initialVersion

    private var library: VariableMap? = null
    private val protectedNames: HashSet<String> = HashSet()
    private var variables: VariableMap = VariableMap()

    init {
        initialVariables.keys.forEach { protectName(it) }
        protectName("version")
        variables["version"] = IntegerVariable(version)
    }

    override fun protectName(key: String) {
        protectedNames.add(key)
    }

    override fun implementsLibrary(library: VariableMap) {
        library.keys.forEach { protectName(it) }
        this.library = library
    }

    override fun getVariables() = variables

    override fun resolve(key: String, returnMemoryAddress: Boolean): Variable<*> {
        val resolvedVariable = variables[key] ?: library?.get(key) ?: staticVariables[key] ?: initialVariables[key]

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
        if (isStatic && staticVersions[key] == version) throw NameError("name '$key' is already static")
        if (protectedNames.contains(key)) throw NameError("name '$key' is protected")

        val baseIsStatic = staticVersions.containsKey(key)

        if ((isStatic || baseIsStatic) && !value.isSerializable())
            throw ValueError("only primitive variables can be declared as static")

        if (isStatic) staticVersions[key] = version

        if (baseIsStatic && !isStatic || isStatic && !baseIsStatic)
            staticVariables[key] = value
        else if (!isStatic)
            variables[key] = value

    }

    override fun remove(key: String) {
        variables.remove(key)
    }

    override fun snapshot() {
        version++
        variables = VariableMap()
        variables["version"] = IntegerVariable(version)
    }

    override fun createChildren(initialVariables: VariableMap): Scope {
        return DefaultScope(this, initialVariables)
    }

}
