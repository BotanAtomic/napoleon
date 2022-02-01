package io.deepn.script.stdlib

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
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

    val functions: MutableList<NativeFunction> = ArrayList()

    init {
        load(IO::class)
        load(Math::class)
        load(Utils::class)
        load(List::class)
    }

    private fun load(libraryClass: KClass<*>) {
        val packageName = libraryClass.findAnnotation<Package>()?.value
        functions.addAll(libraryClass.members.filter { it.isFinal }.map { function ->
            val name = function.findAnnotation<FunctionName>()?.value ?: function.name
            NativeFunction(function, libraryClass.objectInstance, name, packageName)
        })
    }

}
