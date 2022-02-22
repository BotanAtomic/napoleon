package io.deepn.script.stdlib

import io.deepn.script.DefaultExecutionEnvironment
import io.deepn.script.scope.VariableMap
import io.deepn.script.variables.Variable
import io.deepn.script.variables.function.LibraryVariable
import io.deepn.script.variables.function.NativeFunctionVariable
import io.deepn.script.variables.primitive.StringVariable
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
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

data class NativeConstant(
    val variable: Variable<*>,
    val name: String,
    val packageName: String?
)

object StandardLibrary {

    private val functionExtensions: MutableMap<KClass<*>, MutableMap<String, NativeFunctionVariable>> = HashMap()

    private val functions: MutableList<NativeFunction> = ArrayList()
    private val constants: MutableList<NativeConstant> = ArrayList()

    init {
        listOf(
            IOLibrary,
            MathLibrary,
            UtilsLibrary,
            ListLibrary,
            JsonLibrary,
            HttpLibrary,
            StringLibrary).forEach { load(it::class) }
    }

    fun findFunctionExtension(variable: Variable<*>, index: StringVariable): Variable<*>? {
        return functionExtensions[variable::class]?.get(index.value)?.createExtension(variable)
    }

    private fun load(libraryClass: KClass<*>) {
        val packageName = libraryClass.findAnnotation<Package>()?.value
        functions.addAll(libraryClass.members.filter { it.isFinal && it !is KProperty<*> }.map { member ->
            val name = member.findAnnotation<FunctionName>()?.value ?: member.name
            NativeFunction(member, libraryClass.objectInstance, name, packageName)
        })

        constants.addAll(libraryClass.members.filter { it.isFinal && it is KProperty<*> }.map { member ->
            val name = member.findAnnotation<FunctionName>()?.value ?: member.name
            val returnValue = member.call(libraryClass.objectInstance) as Variable<*>
            NativeConstant(returnValue, name, packageName)
        })

    }

    fun generateLibrary(environment: DefaultExecutionEnvironment): VariableMap {
        val variables = VariableMap()

        fun addToPackage(packageName: String, name: String, variable: Variable<*>) {
            variables.getOrPut(packageName) { LibraryVariable(packageName) }
                .setIndex(StringVariable(name), variable)
        }

        functions.forEach {
            val extensionReceiverParameter = it.function.extensionReceiverParameter?.type?.classifier
            val functionVariable = NativeFunctionVariable(it, environment)

            when {
                extensionReceiverParameter is KClass<*> -> functionExtensions
                    .getOrPut(extensionReceiverParameter) { HashMap() }[it.name] = functionVariable
                it.packageName == null -> variables[it.name] = functionVariable
                else -> addToPackage(it.packageName, it.name, functionVariable)
            }
        }

        constants.forEach {
            if (it.packageName == null) variables[it.name] = it.variable
            else addToPackage(it.packageName, it.name, it.variable)
        }
        return variables
    }

}
