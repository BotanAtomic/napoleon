package io.deepn.flow.error

open class FlowError(error: Any? = null) : RuntimeException(error.toString())

class SyntaxError(message: String) : FlowError(message)

class TypeError(message: String) : FlowError(message)

class IndexError(message: String) : FlowError(message)

class KeyError(message: String) : FlowError(message)

class NameError(message : String) : FlowError(message)

class ArgumentTypeError(message : String) : FlowError(message)

class ValueError(message : String) : FlowError(message)

class FunctionCallError(message : String) : FlowError(message)

class JsonError(message : String) : FlowError(message)

class UnknownError(message : String?) : FlowError(message)
