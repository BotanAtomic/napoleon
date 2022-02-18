package io.deepn.script.scope

import io.deepn.script.error.FunctionCallError
import io.deepn.script.error.NameError
import io.deepn.script.variables.Variable
import io.deepn.script.variables.Void
import io.deepn.script.variables.function.NativeFunctionVariable
import io.deepn.script.variables.memory.MemoryAddressVariable
import io.deepn.script.variables.primitive.StringVariable
import kotlin.reflect.KClass

val functionExtensions: MutableMap<KClass<*>, MutableMap<String, NativeFunctionVariable>> = HashMap()

fun findFunctionExtension(variable: Variable<*>, index: StringVariable): Variable<*> {
    return functionExtensions[variable::class]?.get(index.value)?.createExtension(variable)
        ?: throw FunctionCallError("'${variable.type()}' has no function ${index.value}()")
}

class Scope(private val parent: Scope? = null, initialVariables: HashMap<String, Variable<*>>? = null) {

    private val protectedVariables = HashSet<String>()
    private val staticVariables = HashMap<String, Int>()

    private var version = 0

    val variables = HashMap<String, Variable<*>>().apply {
        if (initialVariables != null)
            putAll(initialVariables)
        initialVariables?.keys?.forEach { protectName(it) }
    }

    fun resolveOrCreate(key: String, toCreate: Variable<*>): Variable<*> {
        if (resolve(key) == Void)
            assign(key, toCreate)

        return resolve(key)
    }

    fun resolve(key: String, returnMemoryAddress: Boolean = false): Variable<*> {
        if (!variables.containsKey(key) && parent != null)
            return parent.resolve(key, returnMemoryAddress)

        return variables[key] ?: if (returnMemoryAddress) MemoryAddressVariable { value, isStatic ->
            assign(key, value, if (isStatic is Boolean) isStatic else false)
        } else Void
    }

    fun assign(key: String, variable: Variable<*>, isStatic: Boolean = false) {
        if (isStatic && staticVariables[key] == version) throw NameError("name '$key' is already static")
        if (protectedVariables.contains(key)) throw NameError("name '$key' is protected")

        if(!isStatic || !staticVariables.containsKey(key))
            variables[key] = variable

        println("assign $key")

        if (isStatic) staticVariables[key] = version
    }

    fun remove(key: String) {
        variables.remove(key)
    }

    fun protectName(key: String) {
        protectedVariables.add(key)
    }

    fun snapshot() {
        version++
    }

    override fun toString(): String {
        val builder = StringBuilder("---Scope (${variables.size})---\n")

        variables.forEach {
            builder.append("${it.key} -> ${it.value}\n")
        }

        return builder.append("-----------").toString()
    }

}
