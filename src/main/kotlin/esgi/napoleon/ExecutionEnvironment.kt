package esgi.napoleon

import esgi.napoleon.error.*
import esgi.napoleon.generated.NapoleonLexer
import esgi.napoleon.generated.NapoleonParser
import esgi.napoleon.logger.CachedLogger
import esgi.napoleon.logger.Logger
import esgi.napoleon.logger.SYSTEM_LOGGER
import esgi.napoleon.plot.PlotManager
import esgi.napoleon.scope.Scope
import esgi.napoleon.scope.VariableMap
import esgi.napoleon.scope.impl.DefaultScope
import esgi.napoleon.stdlib.StandardLibrary
import esgi.napoleon.stdlib.libs.PlotLibrary.show
import esgi.napoleon.strategy.DefaultStrategyHandler
import esgi.napoleon.strategy.EmptyStrategyHandler
import esgi.napoleon.strategy.StrategyHandler
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.plot.PlotVariable
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.util.concurrent.locks.ReentrantLock
import kotlin.system.measureTimeMillis

data class NapoleonCompilationResult(
    val errors: List<GrammarSyntaxError>,
    val time: Long,
    val success: Boolean = errors.isEmpty()
)

data class NapoleonExecutionResult(
    val compilationResult: NapoleonCompilationResult,
    val content: String,
    val error: NapoleonExecutionError?,
    val time: Long,
    val result: String?,
    val plots: List<String>,
    val success: Boolean = (error == null),
)

interface ExecutionEnvironment {

    fun execute(source: String, toInject: VariableMap? = null): NapoleonExecutionResult

    fun getScope(): Scope

    fun getLogger(): Logger

    fun getStrategyHandler(): StrategyHandler

    fun getPlotManager() : PlotManager

}

class DefaultExecutionEnvironment : ExecutionEnvironment {

    private val stackTrace = StackTrace()

    private val standardLibrary = StandardLibrary.generateLibrary(this)

    private val scope = DefaultScope()

    private val logger: CachedLogger = CachedLogger()

    private val strategyHandler: StrategyHandler = DefaultStrategyHandler(logger)

    private val plotManager: PlotManager = PlotManager()

    private val locker = ReentrantLock()

    init {
        scope.implementsLibrary(standardLibrary)
    }

    override fun execute(source: String, toInject: VariableMap?): NapoleonExecutionResult {
        locker.lock()
        val errorHandler = NapoleonErrorHandler()

        val t0 = System.currentTimeMillis()
        val lexer = NapoleonLexer(CharStreams.fromString(source))
        lexer.errorListeners.clear()
        lexer.addErrorListener(errorHandler)
        val parser = NapoleonParser(CommonTokenStream(lexer))
        parser.errorHandler = NapoleonErrorStrategyHandler()
        parser.errorListeners.clear()
        parser.addErrorListener(errorHandler)
        val context = parser.chunk()

        val compilationResult = NapoleonCompilationResult(errorHandler.exceptions, System.currentTimeMillis() - t0)

        if(compilationResult.success.not()) {
            locker.unlock()
            return NapoleonExecutionResult(compilationResult, "", null, 0, null, plots = listOf()).also {
                logger.reset()
                stackTrace.reset()
                plotManager.reset()
            }
        }

        toInject?.forEach { (key, value) ->
            scope.getVariables()[key] = value
            scope.protectName(key)
        }
        val visitor = Visitor(context, scope, stackTrace, strategyHandler)
        stackTrace.stack(visitor)
        var returnedVariable: Variable<*>? = null
        var error: NapoleonError? = null

        val time = measureTimeMillis {
            kotlin.runCatching { visitor.visit(context) }.let {
                returnedVariable = it.getOrNull()
                it.exceptionOrNull()?.let { throwable ->
                    error = if (throwable is NapoleonError)
                        throwable
                    else
                        UnknownError(throwable.message).also { throwable.printStackTrace() }
                }
            }
        }

        val compilationException = error?.let { NapoleonError ->
            NapoleonExecutionError(
                NapoleonError.javaClass.simpleName,
                NapoleonError.message ?: "",
                stackTrace.traces.map {
                    ErrorLine(
                        it.currentContext.start.toScriptToken(),
                        it.currentContext.stop.toScriptToken(),
                        kotlin.runCatching { source.split("\n")[it.currentContext.start.line - 1].trim()}.getOrDefault(""),
                    )
                }
            )
        }

        if(returnedVariable is PlotVariable)
            (returnedVariable as PlotVariable).show(this)

        if(returnedVariable == esgi.napoleon.variables.Void)
            returnedVariable = null

        locker.unlock()
        return NapoleonExecutionResult(compilationResult, logger.toString(),
            compilationException, time, returnedVariable?.valueToString(), plots = ArrayList(plotManager.plots)).also {
            logger.reset()
            stackTrace.reset()
            plotManager.reset()
        }
    }

    override fun getLogger() = logger

    override fun getScope() = scope

    override fun getStrategyHandler() = strategyHandler

    override fun getPlotManager() = plotManager

}
