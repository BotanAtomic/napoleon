package io.deepn.flow.variables.error

import io.deepn.flow.error.FlowError
import io.deepn.flow.variables.Variable

class ErrorVariable(error: FlowError) : Variable<FlowError>(error) {
    override fun type() = "error"
}
