package io.deepn.flow.stdlib

import io.deepn.flow.DefaultExecutionEnvironment
import io.deepn.flow.error.ValueError
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.stdlib.libs.*
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.function.LibraryVariable
import io.deepn.flow.variables.function.NativeFunctionVariable
import io.deepn.flow.variables.primitive.StringVariable
import io.deepn.flow.variables.primitive.api.NumberVariable
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.full.extensionReceiverParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Environment

@Target(AnnotationTarget.FUNCTION)
annotation class FunctionName(val value: String)

@Target(AnnotationTarget.CLASS)
annotation class Package(val value: String)

@Target(AnnotationTarget.FUNCTION)
annotation class Cached

object Filter {

    @Target(AnnotationTarget.VALUE_PARAMETER)
    annotation class StrictPositive

    fun apply(parameter: KParameter, variable: Any?) {
        if (variable == null) return

        if (parameter.hasAnnotation<StrictPositive>() && variable is NumberVariable)
            if (variable.toDouble() <= 0.0) throw ValueError("argument '${parameter.name}' must be strictly positive")
    }

}

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
            StringLibrary,
            DateLibrary,
            TechnicalAnalysisLibrary,
            StorageLibrary
        ).forEach { load(it::class) }
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
