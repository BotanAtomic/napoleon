package io.deepn.script

import io.deepn.script.error.*
import io.deepn.script.generated.DeepScriptLexer
import io.deepn.script.generated.DeepScriptParser
import io.deepn.script.logger.Logger
import io.deepn.script.logger.SYSTEM_LOGGER
import io.deepn.script.scope.Scope
import io.deepn.script.scope.impl.BufferedScope
import io.deepn.script.stdlib.StandardLibrary
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

interface ExecutionEnvironment {

    fun compile(): DeepScriptCompilationResult

    fun execute(): DeepScriptExecutionResult

}

class DefaultExecutionEnvironment(
    private val source: String,
    val scope: Scope = BufferedScope(),
    val logger: Logger = SYSTEM_LOGGER
) : ExecutionEnvironment {

    private val stackTrace = StackTrace()

    private var context: DeepScriptParser.ChunkContext? = null
    private val errorHandler = DeepScriptErrorHandler()

    private val standardLibrary = StandardLibrary.generateLibrary(this)

    init {
        scope.implementsLibrary(standardLibrary)
    }

    override fun compile(): DeepScriptCompilationResult {
        if (this.context != null) return DeepScriptCompilationResult(ArrayList(), 0)
        val time = measureTimeMillis {
            val lexer = DeepScriptLexer(CharStreams.fromString(source))
            lexer.errorListeners.clear()
            lexer.addErrorListener(errorHandler)
            val parser = DeepScriptParser(CommonTokenStream(lexer))
            parser.errorHandler = DeepScriptStrategyHandler()
            parser.errorListeners.clear()
            parser.addErrorListener(errorHandler)
            this.context = parser.chunk()
        }
        return DeepScriptCompilationResult(errorHandler.exceptions, time)
    }

    override fun execute(): DeepScriptExecutionResult {
        context?.let { context ->
            val visitor = Visitor(context, scope, stackTrace)
            stackTrace.stack(visitor)
            var returnedVariable: Variable<*>? = null
            var error: DeepScriptError? = null

            val time = measureTimeMillis {
                kotlin.runCatching { visitor.visit(context) }.let {
                    returnedVariable = it.getOrNull()
                    it.exceptionOrNull()?.let { throwable ->
                        error = if (throwable is DeepScriptError)
                            throwable
                        else
                            UnknownError(throwable.message).also { throwable.printStackTrace() }
                    }
                }
            }

            val compilationException = error?.let { deepScriptError ->
                DeepScriptExecutionError(
                    deepScriptError.javaClass.simpleName,
                    deepScriptError.message ?: "",
                    stackTrace.traces.map {
                        ErrorLine(
                            it.currentContext.start.toScriptToken(),
                            it.currentContext.stop.toScriptToken(),
                            source.split("\n")[it.currentContext.start.line - 1].trim(),
                        )
                    }
                )
            }

            scope.snapshot()
            return DeepScriptExecutionResult(compilationException, time, returnedVariable)
        } ?: throw RuntimeException("script is not compiled")
    }

}
