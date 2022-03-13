package io.deepn.flow.storage

import io.deepn.flow.variables.Variable

interface Storage {

    fun init(key: String, variable: Variable<*>)

    fun get(key: String) : Variable<*>

    fun set(key: String, variable: Variable<*>)

}
