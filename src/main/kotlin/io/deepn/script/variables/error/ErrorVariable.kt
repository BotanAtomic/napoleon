package io.deepn.script.variables.error

import io.deepn.script.error.DeepScriptError
import io.deepn.script.variables.Variable

class ErrorVariable(error: DeepScriptError) : Variable<DeepScriptError>(error) {
    override fun type() = "error"
}
