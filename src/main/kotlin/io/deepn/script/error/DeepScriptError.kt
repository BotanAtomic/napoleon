package io.deepn.script.error

open class DeepScriptError(error: Any? = null) : RuntimeException(error.toString())

class SyntaxError(message: String) : DeepScriptError(message)

class TypeError(message: String) : DeepScriptError(message)

class IndexError(message: String) : DeepScriptError(message)

class KeyError(message: String) : DeepScriptError(message)

class NameError(message : String) : DeepScriptError(message)

class ArgumentTypeError(message : String) : DeepScriptError(message)

class ValueError(message : String) : DeepScriptError(message)

class FunctionCallError(message : String) : DeepScriptError(message)

class UnknownError(message : String?) : DeepScriptError(message)




