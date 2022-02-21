package io.deepn.script.stdlib

import io.deepn.script.DefaultExecutionEnvironment
import io.deepn.script.scope.VariableMap
import io.deepn.script.variables.Variable
import io.deepn.script.variables.function.LibraryVariable
import io.deepn.script.variables.function.NativeFunctionVariable
import io.deepn.script.variables.primitive.StringVariable
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.extensionReceiverParameter
import kotlin.reflect.full.findAnnotation

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Environment

@Target(AnnotationTarget.FUNCTION)
annotation class FunctionName(val value: String)


@Target(AnnotationTarget.CLASS)
annotation class Package(val value: String)

data class NativeFunction(
    val function: KCallable<*>,
    val instance: Any?,
    val name: String,
    val packageName: String?
)

object StandardLibrary {

    private val functionExtensions: MutableMap<KClass<*>, MutableMap<String, NativeFunctionVariable>> = HashMap()

    private val functions: MutableList<NativeFunction> = ArrayList()

    init {
        listOf(IO, Math, Utils, List, Json).forEach { load(it::class) }
    }


    fun findFunctionExtension(variable: Variable<*>, index: StringVariable): Variable<*>? {
        return functionExtensions[variable::class]?.get(index.value)?.createExtension(variable)
    }

    private fun load(libraryClass: KClass<*>) {
        val packageName = libraryClass.findAnnotation<Package>()?.value
        functions.addAll(libraryClass.members.filter { it.isFinal }.map { function ->
            val name = function.findAnnotation<FunctionName>()?.value ?: function.name
            NativeFunction(function, libraryClass.objectInstance, name, packageName)
        })
    }

    fun generateLibrary(environment: DefaultExecutionEnvironment): VariableMap {
        val variables = VariableMap()
        functions.forEach {
            val extensionReceiverParameter = it.function.extensionReceiverParameter?.type?.classifier
            val functionVariable = NativeFunctionVariable(it, environment)
            if (extensionReceiverParameter is KClass<*>) {
                functionExtensions.getOrPut(extensionReceiverParameter) { HashMap() }[it.name] = functionVariable
            } else if (it.packageName == null) {
                variables[it.name] = functionVariable
            } else {
                val packageVariable = variables.getOrPut(it.packageName) { LibraryVariable(it.packageName) }
                packageVariable.setIndex(StringVariable(it.name), functionVariable)
            }
        }
        return variables
    }

}
