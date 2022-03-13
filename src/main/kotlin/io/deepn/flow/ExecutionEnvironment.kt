package io.deepn.flow

import io.deepn.flow.error.*
import io.deepn.flow.generated.FlowLexer
import io.deepn.flow.generated.FlowParser
import io.deepn.flow.logger.Logger
import io.deepn.flow.logger.SYSTEM_LOGGER
import io.deepn.flow.scope.Scope
import io.deepn.flow.scope.VariableMap
import io.deepn.flow.scope.impl.DefaultScope
import io.deepn.flow.stdlib.StandardLibrary
import io.deepn.flow.storage.Storage
import io.deepn.flow.storage.impl.DefaultStorage
import io.deepn.flow.strategy.EmptyStrategyHandler
import io.deepn.flow.strategy.StrategyHandler
import io.deepn.flow.variables.Variable
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import kotlin.system.measureTimeMillis

data class FlowCompilationResult(
    val errors: List<GrammarSyntaxError>,
    val time: Long,
    val success: Boolean = errors.isEmpty()
)

data class FlowExecutionResult(
    val error: FlowExecutionError?,
    val time: Long,
    val result: Variable<*>?,
    val success: Boolean = (error == null)
)

interface ExecutionEnvironment {

    fun compile(): FlowCompilationResult

    fun execute(toInject: VariableMap? = null): FlowExecutionResult

    fun getScope(): Scope

    fun getLogger(): Logger

    fun getStrategyHandler(): StrategyHandler

    fun getStorage(): Storage

}

class DefaultExecutionEnvironment(
    private val source: String,
    private val scope: Scope = DefaultScope(),
    private val logger: Logger = SYSTEM_LOGGER,
    private val strategyHandler: StrategyHandler = EmptyStrategyHandler(),
    private val storage : Storage = DefaultStorage()
) : ExecutionEnvironment {

    private val stackTrace = StackTrace()

    private var context: FlowParser.ChunkContext? = null
    private val errorHandler = FlowErrorHandler()

    private val standardLibrary = StandardLibrary.generateLibrary(this)

    init {
        scope.implementsLibrary(standardLibrary)
    }

    override fun compile(): FlowCompilationResult {
        if (this.context != null) return FlowCompilationResult(ArrayList(), 0)
        val time = measureTimeMillis {
            val lexer = FlowLexer(CharStreams.fromString(source))
            lexer.errorListeners.clear()
            lexer.addErrorListener(errorHandler)
            val parser = FlowParser(CommonTokenStream(lexer))
            parser.errorHandler = FlowErrorStrategyHandler()
            parser.errorListeners.clear()
            parser.addErrorListener(errorHandler)
            this.context = parser.chunk()
        }
        return FlowCompilationResult(errorHandler.exceptions, time)
    }

    override fun execute(toInject: VariableMap?): FlowExecutionResult {
        context?.let { context ->
            toInject?.forEach { (key, value) ->
                scope.getVariables()[key] = value
                scope.protectName(key)
            }
            val visitor = Visitor(context, scope, stackTrace, strategyHandler)
            stackTrace.stack(visitor)
            var returnedVariable: Variable<*>? = null
            var error: FlowError? = null

            val time = measureTimeMillis {
                kotlin.runCatching { visitor.visit(context) }.let {
                    returnedVariable = it.getOrNull()
                    it.exceptionOrNull()?.let { throwable ->
                        error = if (throwable is FlowError)
                            throwable
                        else
                            UnknownError(throwable.message).also { throwable.printStackTrace() }
                    }
                }
            }

            val compilationException = error?.let { flowError ->
                FlowExecutionError(
                    flowError.javaClass.simpleName,
                    flowError.message ?: "",
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
            return FlowExecutionResult(compilationException, time, returnedVariable)
        } ?: throw RuntimeException("script is not compiled")
    }

    override fun getLogger() = logger

    override fun getScope() = scope

    override fun getStrategyHandler() = strategyHandler

    override fun getStorage() = storage

}
