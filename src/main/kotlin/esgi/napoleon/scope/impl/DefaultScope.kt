package esgi.napoleon.scope.impl

import esgi.napoleon.error.NameError
import esgi.napoleon.scope.Scope
import esgi.napoleon.scope.VariableMap
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.Void
import esgi.napoleon.variables.memory.MemoryAddressVariable

class DefaultScope(
    private val parent: Scope? = null,
    val initialVariables: VariableMap = VariableMap(),
    val staticVersions: HashMap<String, Int> = HashMap(),
    val staticVariables: VariableMap = VariableMap(),
) : Scope {


    private var library: VariableMap? = null
    private val protectedNames: HashSet<String> = HashSet()
    private var variables: VariableMap = VariableMap()

    init {
        initialVariables.keys.forEach { protectName(it) }
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
            return MemoryAddressVariable { value, _ ->
                assign(key, value)
            }

        resolvedVariable?.name = key

        return resolvedVariable ?: Void
    }

    override fun assign(key: String, value: Variable<*>) {
        if (protectedNames.contains(key)) throw NameError("name '$key' is protected")

        variables[key] = value

    }

    override fun remove(key: String) {
        variables.remove(key)
    }

    override fun createChildren(initialVariables: VariableMap): Scope {
        return DefaultScope(this, initialVariables)
    }

}
