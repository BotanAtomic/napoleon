package io.deepn.script

import io.deepn.script.error.*
import io.deepn.script.generated.DeepScriptLexer
import io.deepn.script.generated.DeepScriptParser
import io.deepn.script.logger.Logger
import io.deepn.script.logger.SYSTEM_LOGGER
import io.deepn.script.scope.Scope
import io.deepn.script.stdlib.StandardLibrary
import io.deepn.script.utils.implementFunctions
import io.deepn.script.variables.Variable
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import kotlin.system.measureTimeMillis

data class DeepScriptCompilationResult(
    val errors: List<GrammarSyntaxError>,
    val time: Long,
    val success: Boolean = errors.isEmpty()
)

data class DeepScriptExecutionResult(
    val error: DeepScriptExecutionError?,
    val time: Long,
    val result: Variable<*>?,
    val success: Boolean = (error == null)
)

class DeepScriptEnvironment(
    private val source: String,
    private val scope: Scope = Scope(),
    val logger: Logger = SYSTEM_LOGGER
) {

    val stackTrace = StackTrace()

    private var context: DeepScriptParser.ChunkContext? = null
    private val errorHandler = DeepScriptErrorHandler()

    fun compile(): DeepScriptCompilationResult {
        if (this.context != null) return DeepScriptCompilationResult(ArrayList(), 0)
        val time = measureTimeMillis {
            val lexer = DeepScriptLexer(CharStreams.fromString(source))
            lexer.errorListeners.clear()
            lexer.addErrorListener(errorHandler)
            val parser = DeepScriptParser(CommonTokenStream(lexer))
            parser.errorListeners.clear()
            parser.addErrorListener(errorHandler)
            this.context = parser.chunk()
        }
        scope.implementFunctions(StandardLibrary.functions, this)
        return DeepScriptCompilationResult(errorHandler.exceptions, time)
    }

    fun execute(): DeepScriptExecutionResult {
        context?.let { context ->
            val visitor = Visitor(context, scope)
            var returnedVariable: Variable<*>? = null
            var error: DeepScriptError? = null

            val time = measureTimeMillis {
                kotlin.runCatching { visitor.visit(context) }.let {
                    returnedVariable = it.getOrNull()
                    it.exceptionOrNull()?.let { throwable ->
                        error = if (throwable is DeepScriptError)
                            throwable
                        else
                            UnknownError(throwable.message)
                    }
                }
            }

            val compilationException = error?.let { deepScriptError ->
                val lastContext = visitor.currentContext
                DeepScriptExecutionError(
                    deepScriptError.javaClass.simpleName,
                    deepScriptError.message ?: "",
                    lastContext.start.toScriptToken(),
                    lastContext.stop.toScriptToken(),
                    lastContext.text,
                )
            }

            return DeepScriptExecutionResult(compilationException, time, returnedVariable)
        } ?: throw RuntimeException("script is not compiled")
    }

}
