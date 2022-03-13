package io.deepn.flow.stdlib.libs

import io.deepn.flow.ExecutionEnvironment
import io.deepn.flow.stdlib.Environment
import io.deepn.flow.stdlib.Package
import io.deepn.flow.variables.Variable
import io.deepn.flow.variables.primitive.StringVariable

@Package("storage")
object StorageLibrary {

    fun init(key: StringVariable, value: Variable<*>, @Environment environment: ExecutionEnvironment) {
        environment.getStorage().init(key.value, value)
    }

    fun get(key: StringVariable, @Environment environment: ExecutionEnvironment) = environment.getStorage().get(key.value)

    fun set(key: StringVariable, value: Variable<*>, @Environment environment: ExecutionEnvironment) {
        environment.getStorage().set(key.value, value)
    }

//    fun update(key: StringVariable, updateFunction: LocalFunctionVariable, @Environment environment: ExecutionEnvironment) {
//        environment.getStorage().let {
//            it.set(key.value, updateFunction.call(createFunctionArguments(null to it.get(key.value))))
//        }
//    }
//
//    fun append(key: String, variable: Variable<*>) {
//        val list = get(key)
//
//        if (list !is ListVariable)
//            throw ValueError("variable at key '${key}' must be a list, not '${list.type()}'")
//
//        list.insert(variable)
//    }

}
