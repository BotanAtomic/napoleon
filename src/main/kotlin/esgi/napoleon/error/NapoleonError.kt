package esgi.napoleon.error

open class NapoleonError(error: Any? = null) : RuntimeException(error.toString())

class SyntaxError(message: String) : NapoleonError(message)

class TypeError(message: String) : NapoleonError(message)

class IndexError(message: String) : NapoleonError(message)

class KeyError(message: String) : NapoleonError(message)

class NameError(message : String) : NapoleonError(message)

class ArgumentTypeError(message : String) : NapoleonError(message)

class ValueError(message : String) : NapoleonError(message)

class FunctionCallError(message : String) : NapoleonError(message)

class JsonError(message : String) : NapoleonError(message)

class UnknownError(message : String?) : NapoleonError(message)
