package io.deepn.script

import io.deepn.script.error.DeepScriptError
import io.deepn.script.error.DeepScriptErrorHandler
import io.deepn.script.error.GrammarSyntaxError
import io.deepn.script.error.UnknownError
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
    val error: DeepScriptError?,
    val time: Long,
    val result: Variable<*>?,
    val success: Boolean = (error == null)
)

class DeepScriptEnvironment(
    private val source: String,
    private val scope: Scope = Scope(),
    val logger: Logger = SYSTEM_LOGGER
) {

    private var context: DeepScriptParser.ChunkContext? = null
    private val errorHandler = DeepScriptErrorHandler()

    fun compile(): DeepScriptCompilationResult {
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
        val visitor = Visitor(scope)
        var returnedVariable: Variable<*>? = null
        var error: DeepScriptError? = null
        val time = measureTimeMillis {

            try {
                returnedVariable = visitor.visit(context)
            } catch (e: DeepScriptError) {
                e.printStackTrace()
                error = e
                val lastContext = visitor.currentContext.get()
                if (lastContext != null) {
                    println("Error: ${lastContext.start.line} [${lastContext.text}]")
                }
            } catch (e: Exception) {
                error = UnknownError(e.message)
            }
        }

        return DeepScriptExecutionResult(error, time, returnedVariable)
    }

}
