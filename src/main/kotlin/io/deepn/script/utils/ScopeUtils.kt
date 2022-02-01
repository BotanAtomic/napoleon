package io.deepn.script.utils

import io.deepn.script.DeepScriptEnvironment
import io.deepn.script.scope.Scope
import io.deepn.script.scope.functionExtensions
import io.deepn.script.stdlib.NativeFunction
import io.deepn.script.variables.function.LibraryVariable
import io.deepn.script.variables.function.NativeFunctionVariable
import io.deepn.script.variables.primitive.StringVariable
import kotlin.reflect.KClass
import kotlin.reflect.full.extensionReceiverParameter


fun Scope.implementFunctions(functions: List<NativeFunction>, environment: DeepScriptEnvironment) {
    functions.forEach {
        val extensionReceiverParameter = it.function.extensionReceiverParameter?.type?.classifier
        val functionVariable = NativeFunctionVariable(it, environment)
        if (extensionReceiverParameter is KClass<*>) {
            functionExtensions.getOrPut(extensionReceiverParameter) { HashMap() }[it.name] = functionVariable
        } else if (it.packageName == null) {
            this.assign(it.name, functionVariable)
            this.protectName(it.name)
        } else {
            val packageVariable = this.resolveOrCreate(it.packageName, LibraryVariable(it.packageName))
            packageVariable.setIndex(StringVariable(it.name), functionVariable)
            this.protectName(it.packageName)
        }

    }
}
